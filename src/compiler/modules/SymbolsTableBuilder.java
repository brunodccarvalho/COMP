package compiler.modules;

import static jjt.jmmTreeConstants.JJTCLASSBODY;
import static jjt.jmmTreeConstants.JJTCLASSHEADER;
import static jjt.jmmTreeConstants.JJTCLASSMETHODDECLARATIONS;
import static jjt.jmmTreeConstants.JJTCLASSTYPE;
import static jjt.jmmTreeConstants.JJTCLASSVARDECLARATION;
import static jjt.jmmTreeConstants.JJTCLASSVARDECLARATIONS;
import static jjt.jmmTreeConstants.JJTEXTENDS;
import static jjt.jmmTreeConstants.JJTIDENTIFIER;
import static jjt.jmmTreeConstants.JJTMAINDECLARATION;
import static jjt.jmmTreeConstants.JJTMAINPARAMETERLIST;
import static jjt.jmmTreeConstants.JJTMETHODBODY;
import static jjt.jmmTreeConstants.JJTMETHODDECLARATION;
import static jjt.jmmTreeConstants.JJTMETHODNAME;
import static jjt.jmmTreeConstants.JJTPARAMETER;
import static jjt.jmmTreeConstants.JJTPARAMETERLIST;
import static jjt.jmmTreeConstants.JJTRETURNSTATEMENT;
import static jjt.jmmTreeConstants.JJTVARIABLEDECLARATION;

import compiler.exceptions.CompilationException;
import compiler.symbols.FunctionLocals;
import compiler.symbols.FunctionSignature;
import compiler.symbols.JMMCallableDescriptor;
import compiler.symbols.JMMClassDescriptor;
import compiler.symbols.JMMFunction;
import compiler.symbols.JMMMainDescriptor;
import compiler.symbols.JMMMethodDescriptor;
import compiler.symbols.LocalDescriptor;
import compiler.symbols.MemberDescriptor;
import compiler.symbols.TypeDescriptor;
import compiler.symbols.UnknownClassDescriptor;
import jjt.SimpleNode;

/**
 * Compiler module that populates the jmm class descriptor's symbol tables (data
 * members, member methods, their signatures), constructs a map of methods to
 * SimpleNodes, and populates the local symbol tables for each of the methods.
 */
public class SymbolsTableBuilder extends CompilationStatus {
  final CompilationData data;

  SymbolsTableBuilder(CompilationData data) {
    this.data = data;
  }

  SymbolsTableBuilder read(CompilationStatus tracker) {
    readClassHeader();
    readClassMemberVariables();
    readClassMethodDeclarations();
    readMethodLocals();
    tracker.update(status());
    return this;
  }

  private void readClassHeader() {
    SimpleNode classHeaderNode = data.classNode.jjtGetChild(0);
    assert classHeaderNode.is(JJTCLASSHEADER);

    SimpleNode classTypeNode = classHeaderNode.jjtGetChild(0);
    assert classTypeNode.is(JJTCLASSTYPE);

    String className = classTypeNode.jjtGetVal();

    if (TypeDescriptor.exists(className)) {
      DiagnosticsHandler.invalidClassname(classTypeNode, className);
      throw new CompilationException("Invalid class name " + className);
    }

    // TODO: Extends clause...
    if (classHeaderNode.jjtGetNumChildren() == 1) {
      data.jmmClass = new JMMClassDescriptor(className);
    } else {
      SimpleNode extendsNode = classHeaderNode.jjtGetChild(1);
      assert extendsNode.is(JJTEXTENDS);

      SimpleNode superNode = extendsNode.jjtGetChild(0);
      assert superNode.is(JJTCLASSTYPE);

      String superName = superNode.jjtGetVal();

      if (TypeDescriptor.exists(superName)) {
        DiagnosticsHandler.invalidSuperClassname(superNode, superName);
        throw new CompilationException("Invalid class name " + superName);
      }

      UnknownClassDescriptor superClass = new UnknownClassDescriptor(superName);
      data.jmmClass = new JMMClassDescriptor(className, superClass);
    }
  }

  private void readClassMemberVariables() {
    SimpleNode classBodyNode = data.classNode.jjtGetChild(1);
    assert classBodyNode.is(JJTCLASSBODY);

    SimpleNode classVarDeclarationsNode = classBodyNode.jjtGetChild(0);
    assert classVarDeclarationsNode.is(JJTCLASSVARDECLARATIONS);

    for (int i = 0; i < classVarDeclarationsNode.jjtGetNumChildren(); ++i) {
      SimpleNode varDeclaration = classVarDeclarationsNode.jjtGetChild(i);
      readOneClassMemberVariable(varDeclaration);
    }
  }

  private void readClassMethodDeclarations() {
    SimpleNode classBodyNode = data.classNode.jjtGetChild(1);
    assert classBodyNode.is(JJTCLASSBODY);

    SimpleNode classMethodDeclarationsNode = classBodyNode.jjtGetChild(1);
    assert classMethodDeclarationsNode.is(JJTCLASSMETHODDECLARATIONS);

    for (int i = 0; i < classMethodDeclarationsNode.jjtGetNumChildren(); ++i) {
      SimpleNode functionNode = classMethodDeclarationsNode.jjtGetChild(i);
      assert functionNode.is(JJTMETHODDECLARATION) || functionNode.is(JJTMAINDECLARATION);

      if (functionNode.is(JJTMETHODDECLARATION)) {
        readOneClassMethodDeclaration(functionNode);
      } else {
        readClassMainDeclaration(functionNode);
      }
    }
  }

  private void readMethodLocals() {
    for (JMMFunction method : data.nodesMap.keySet()) {
      FunctionLocals locals = readOneMethodLocals(method, data.nodesMap.get(method));
      data.localsMap.put(method, locals);
    }
  }

  private void readOneClassMemberVariable(SimpleNode varDeclarationNode) {
    assert varDeclarationNode.is(JJTCLASSVARDECLARATION);

    SimpleNode typeNode = varDeclarationNode.jjtGetChild(0);
    SimpleNode nameNode = varDeclarationNode.jjtGetChild(1);
    assert nameNode.is(JJTIDENTIFIER);

    String identifier = nameNode.jjtGetVal();

    // ERROR: Repeated class member variable.
    if (data.jmmClass.resolve(identifier) != null) {
      DiagnosticsHandler.memberAlreadyDefined(nameNode, identifier);
      update(Codes.MINOR_ERRORS);
      return;
    }

    TypeDescriptor type = Utils.getOrCreateTypeFromNode(typeNode);
    new MemberDescriptor(type, identifier, data.jmmClass);
  }

  private void readOneClassMethodDeclaration(SimpleNode methodNode) {
    assert methodNode.is(JJTMETHODDECLARATION) && methodNode.jjtGetNumChildren() == 5;

    SimpleNode returnTypeNode = methodNode.jjtGetChild(0);
    SimpleNode methodNameNode = methodNode.jjtGetChild(1);
    SimpleNode paramsListNode = methodNode.jjtGetChild(2);
    SimpleNode methodBodyNode = methodNode.jjtGetChild(3);
    SimpleNode returnStmtNode = methodNode.jjtGetChild(4);
    assert methodNameNode.is(JJTMETHODNAME);
    assert paramsListNode.is(JJTPARAMETERLIST);
    assert methodBodyNode.is(JJTMETHODBODY);
    assert returnStmtNode.is(JJTRETURNSTATEMENT);

    TypeDescriptor returnType = Utils.getOrCreateTypeFromNode(returnTypeNode);
    String name = methodNameNode.jjtGetVal();
    int numParameters = paramsListNode.jjtGetNumChildren();

    // Collect parameters' types and names; build signature.
    TypeDescriptor[] paramTypes = new TypeDescriptor[numParameters];
    String[] paramNames = new String[numParameters];

    for (int i = 0; i < numParameters; ++i) {
      SimpleNode paramNode = paramsListNode.jjtGetChild(i);
      assert paramNode.is(JJTPARAMETER);

      SimpleNode paramTypeNode = paramNode.jjtGetChild(0);
      SimpleNode paramNameNode = paramNode.jjtGetChild(1);
      assert paramNameNode.is(JJTIDENTIFIER);

      TypeDescriptor paramType = Utils.getOrCreateTypeFromNode(paramTypeNode);
      String paramName = paramNameNode.jjtGetVal();

      paramTypes[i] = paramType;
      paramNames[i] = paramName;
    }

    FunctionSignature signature = new FunctionSignature(paramTypes);

    // Error: Repeated methods - Two methods with the same name and signature.
    if (data.jmmClass.hasMethod(name, signature)) {
      DiagnosticsHandler.methodAlreadyDefined(methodNameNode, name, signature);
      update(Codes.MINOR_ERRORS);
      return;
    }

    // Error: Repeated parameter names -- Two parameters have the same name.
    if (!JMMCallableDescriptor.validateParameterNames(paramNames)) {
      DiagnosticsHandler.conflictingParams(methodNameNode, name, signature);
      update(Codes.MINOR_ERRORS);
      return;
    }

    JMMMethodDescriptor method = new JMMMethodDescriptor(name, data.jmmClass, returnType, signature,
                                                         paramNames);
    data.nodesMap.put(method, methodNode);
  }

  private void readClassMainDeclaration(SimpleNode mainNode) {
    assert mainNode.is(JJTMAINDECLARATION) && mainNode.jjtGetNumChildren() == 2;

    SimpleNode paramListNode = mainNode.jjtGetChild(0);
    SimpleNode mainBodyNode = mainNode.jjtGetChild(1);
    assert paramListNode.is(JJTMAINPARAMETERLIST);
    assert mainBodyNode.is(JJTMETHODBODY);

    SimpleNode paramNameNode = paramListNode.jjtGetChild(0);
    assert paramNameNode.is(JJTIDENTIFIER);

    // The dummy name given to parameter String[]
    String paramName = paramNameNode.jjtGetVal();

    // Error: Repeated methods - Two methods with the same name and signature.
    if (data.jmmClass.hasMain()) {
      DiagnosticsHandler.mainMethodDefined(mainNode);
      update(Codes.MINOR_ERRORS);
      return;
    }

    JMMMainDescriptor main = new JMMMainDescriptor(data.jmmClass, paramName);
    data.nodesMap.put(main, mainNode);
  }

  private FunctionLocals readOneMethodLocals(JMMFunction method, SimpleNode methodNode) {
    assert method != null && methodNode != null
        && (methodNode.is(JJTMETHODDECLARATION) || methodNode.is(JJTMAINDECLARATION));

    int index = methodNode.is(JJTMAINDECLARATION) ? 1 : 3;
    SimpleNode methodBodyNode = methodNode.jjtGetChild(index);
    assert methodBodyNode.is(JJTMETHODBODY);

    FunctionLocals locals = new FunctionLocals(method);

    int i;
    for (i = 0; i < methodBodyNode.jjtGetNumChildren(); ++i) {
      SimpleNode varDeclaration = methodBodyNode.jjtGetChild(i);

      // Break when a child which is not a variable declaration is found.
      if (!varDeclaration.is(JJTVARIABLEDECLARATION)) break;

      SimpleNode typeNode = varDeclaration.jjtGetChild(0);
      SimpleNode nameNode = varDeclaration.jjtGetChild(1);
      assert nameNode.is(JJTIDENTIFIER);

      TypeDescriptor type = Utils.getOrCreateTypeFromNode(typeNode);
      String name = nameNode.jjtGetVal();

      // Error: Redefinition of identifier -- same identifier is used twice locally.
      if (locals.hasVariable(name)) {
        DiagnosticsHandler.localAlreadyDefined(nameNode, name, method);
        update(Codes.MINOR_ERRORS);
        continue;
      }

      // Error: Redefinition of parameter -- same identifier is a function parameter.
      if (method.hasParameter(name)) {
        DiagnosticsHandler.paramAlreadyDefined(nameNode, name, method);
        update(Codes.MINOR_ERRORS);
        continue;
      }

      new LocalDescriptor(type, name, locals);
    }

    return locals;
  }

  String getPrint() {
    MemberDescriptor[] members = data.jmmClass.getMembersList();
    JMMMethodDescriptor[] methods = data.jmmClass.getMethodsList();

    StringBuilder string = new StringBuilder();

    string.append("=== CLASS " + data.jmmClass.getClassName() + " SYMBOL TABLE ===\n");

    // Member variables of JMM class
    string.append(">>> Member Variables:\n");
    for (MemberDescriptor member : members) string.append(member).append('\n');

    // Member methods of JMM class
    string.append("\n>>> Member Methods:\n");
    for (JMMMethodDescriptor method : methods) string.append(method).append('\n');

    // Main of JMM class
    if (data.jmmClass.hasMain()) string.append('\n').append(data.jmmClass.getMain()).append('\n');

    // Local tables of all methods
    string.append("\n>>> Function Locals Tables:\n");
    for (FunctionLocals locals : data.localsMap.values()) string.append(locals);

    return string.append('\n').toString();
  }

  void dump() {
    System.out.println(getPrint());
  }
}
