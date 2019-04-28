package compiler.modules;

import static jjt.jmmTreeConstants.*;

import java.util.HashMap;

import compiler.FunctionSignature;
import compiler.DiagnosticsHandler;
import compiler.symbols.*;
import jjt.SimpleNode;

/**
 * Compiler module that populates the jmm class descriptor's symbol tables (data
 * members, member methods, their signatures), constructs a map of methods to
 * SimpleNodes, and populates the local symbol tables for each of the methods.
 */
class SymbolsTable extends CompilerModule {
  private final SimpleNode classNode;
  JMMClassDescriptor jmmClass;
  HashMap<JMMMethodDescriptor, SimpleNode> methodNodesMap = new HashMap<>();
  HashMap<JMMMethodDescriptor, FunctionLocals> methodLocalsMap = new HashMap<>();
  SimpleNode mainNode;
  FunctionLocals mainLocals;

  /**
   * Construct an instance of this class for a class node.
   *
   * The class node should point to a unique class name, or else this stage fails
   * immediately.
   */
  SymbolsTable(SimpleNode classNode) {
    assert classNode.is(JJTCLASSDECLARATION);
    this.classNode = classNode;

    // 1.1: Read ClassHeader: Get class name, extends clause, jmmClass object.
    readClassHeader();
    if (status() >= FATAL) return;

    // 1.2: Read ClassBody's variable declarations:
    // Construct MemberDescriptors for each of the class member variables.
    readClassMemberVariables();
    if (status() >= FATAL) return;

    // 1.3: Read ClassBody's method declarations:
    // Construct MethodDescriptors for each of the class member methods.
    // Construct a JMMMainDescriptor for the main class if it is found.
    // Construct the SimpleNode maps while we're at it.
    readClassMethodDeclarations();
    if (status() >= FATAL) return;

    // 2. Read function local variables into local symbol tables.
    // Construct a FunctionLocals table for each method (and main).
    readMethodLocals();
    if (status() >= FATAL) return;
  }

  private void readClassHeader() {
    SimpleNode classHeader = classNode.jjtGetChild(0);
    assert classHeader.is(JJTCLASSHEADER);

    SimpleNode classType = classHeader.jjtGetChild(0);
    assert classType.is(JJTCLASSTYPE);

    String className = classType.jjtGetVal();

    HashMap<String, TypeDescriptor> typesMap = TypeDescriptor.typesMap;

    // FATAL: The name given to this class is already the name of another type.
    if (TypeDescriptor.exists(className)) {
      System.err.println("Invalid class name " + className);
      status(FATAL);
      return;
    }

    // Extends clause...
    if (classHeader.jjtGetNumChildren() == 1) {
      this.jmmClass = new JMMClassDescriptor(className);
    } else {
      // TODO: Don't know how to handle extends yet...
      this.jmmClass = new JMMClassDescriptor(className);
    }
  }

  private void readClassMemberVariables() {
    SimpleNode classBody = classNode.jjtGetChild(1);
    assert classBody.is(JJTCLASSBODY);

    SimpleNode classVarDeclarations = classBody.jjtGetChild(0);
    assert classVarDeclarations.is(JJTCLASSVARDECLARATIONS);

    for (int i = 0; i < classVarDeclarations.jjtGetNumChildren(); ++i) {
      SimpleNode varDeclaration = classVarDeclarations.jjtGetChild(i);
      readOneClassMemberVariable(varDeclaration);
    }
  }

  private void readOneClassMemberVariable(SimpleNode varDeclarationNode) {
    assert varDeclarationNode.is(JJTCLASSVARDECLARATION);

    SimpleNode typeNode = varDeclarationNode.jjtGetChild(0);
    SimpleNode nameNode = varDeclarationNode.jjtGetChild(1);
    assert nameNode.is(JJTIDENTIFIER);

    String identifier = nameNode.jjtGetVal();

    // ERROR: Repeated class member variable.
    if (jmmClass.resolve(identifier) != null) {
      System.err.println("Error: class variable " + identifier + " is already defined");
      DiagnosticsHandler.self.errorLine(nameNode.jjtGetFirstToken());
      status(MINOR_ERRORS);
      return;
    }

    TypeDescriptor type = getOrCreateTypeFromNode(typeNode);
    MemberDescriptor variable = new MemberDescriptor(type, identifier, jmmClass);
  }

  private void readClassMethodDeclarations() {
    SimpleNode classBody = classNode.jjtGetChild(1);
    assert classBody.is(JJTCLASSBODY);

    SimpleNode classMethodDeclarations = classBody.jjtGetChild(1);
    assert classMethodDeclarations.is(JJTCLASSMETHODDECLARATIONS);

    for (int i = 0; i < classMethodDeclarations.jjtGetNumChildren(); ++i) {
      SimpleNode functionNode = classMethodDeclarations.jjtGetChild(i);
      assert functionNode.is(JJTMETHODDECLARATION) || functionNode.is(JJTMAINDECLARATION);

      if (functionNode.is(JJTMETHODDECLARATION)) {
        readOneClassMethodDeclaration(functionNode);
      } else {
        readClassMainDeclaration(functionNode);
      }
    }
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

    TypeDescriptor returnType = getOrCreateTypeFromNode(returnTypeNode);
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

      TypeDescriptor paramType = getOrCreateTypeFromNode(paramTypeNode);
      String paramName = paramNameNode.jjtGetVal();

      paramTypes[i] = paramType;
      paramNames[i] = paramName;
    }

    FunctionSignature signature = new FunctionSignature(paramTypes);

    // Error: Repeated methods - Two methods with the same name and signature.
    if (jmmClass.hasMethod(name, signature)) {
      System.err.println("Error: method " + name + signature + " is already defined");
      DiagnosticsHandler.self.errorLine(methodNameNode.jjtGetFirstToken());
      status(MINOR_ERRORS);
      return;
    }

    // Error: Repeated parameter names -- Two parameters have the same name.
    if (!JMMCallableDescriptor.validateParameterNames(paramNames)) {
      System.err.println("Error: method " + name + signature + " has conflicting parameter names");
      DiagnosticsHandler.self.errorLine(methodNameNode.jjtGetFirstToken());
      status(MINOR_ERRORS);
      return;
    }

    JMMMethodDescriptor method = new JMMMethodDescriptor(name, jmmClass, returnType, signature,
                                                         paramNames);
    this.methodNodesMap.put(method, methodNode);
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
    if (jmmClass.hasMain()) {
      System.err.println("Error: main method is already defined");
      DiagnosticsHandler.self.errorLine(mainNode.jjtGetFirstToken());
      status(MINOR_ERRORS);
      return;
    }

    JMMMainDescriptor main = new JMMMainDescriptor(jmmClass, paramName);
    this.mainNode = mainNode;
  }

  private void readMethodLocals() {
    for (JMMMethodDescriptor method : methodNodesMap.keySet()) {
      readOneMethodLocals(method);
    }
  }

  private void readOneMethodLocals(JMMMethodDescriptor method) {
    SimpleNode methodNode = methodNodesMap.get(method);
    assert method != null && methodNode != null && methodNode.is(JJTMETHODDECLARATION);

    SimpleNode methodBodyNode = methodNode.jjtGetChild(3);
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

      TypeDescriptor type = getOrCreateTypeFromNode(typeNode);
      String name = nameNode.jjtGetVal();

      // Error: Redefinition of identifier -- same identifier is used twice locally.
      if (locals.hasVariable(name)) {
        System.err.println("Error: " + name + " is already locally defined in " + method);
        DiagnosticsHandler.self.errorLine(nameNode.jjtGetFirstToken());
        status(MINOR_ERRORS);
        continue;
      }

      // Error: Redefinition of parameter -- same identifier is a function parameter.
      if (method.hasParameter(name)) {
        System.err.println("Error: locally defined " + name + " is a parameter of " + method);
        DiagnosticsHandler.self.errorLine(nameNode.jjtGetFirstToken());
        status(MINOR_ERRORS);
        continue;
      }

      LocalDescriptor local = new LocalDescriptor(type, name, locals);
    }

    for (; i < methodBodyNode.jjtGetNumChildren(); ++i) {
      SimpleNode statement = methodBodyNode.jjtGetChild(i);
      System.out.println("is statement");
      if (statement.is(JJTASSIGNMENT)) {
        System.out.println("is assignment");

        SimpleNode leftNode = statement.jjtGetChild(0);
        SimpleNode rightNode = statement.jjtGetChild(1);

        if (leftNode.is(JJTIDENTIFIER) && rightNode.is(JJTIDENTIFIER)) {
          TypeDescriptor leftType = getOrCreateTypeFromNode(leftNode);
          TypeDescriptor rightType = getOrCreateTypeFromNode(rightNode);

          if (leftType != rightType) {
            System.err.println("Error: incompatible types");
            DiagnosticsHandler.self.errorLine(leftNode.jjtGetFirstToken());
            status(MINOR_ERRORS);
            continue;
          }
        }
      }
    }

    methodLocalsMap.put(method, locals);
  }

  @Override
  public String toString() {
    MemberDescriptor[] members = jmmClass.getMembersList();
    JMMMethodDescriptor[] methods = jmmClass.getMethodsList();

    StringBuilder string = new StringBuilder();

    string.append("=== CLASS " + jmmClass.getClassName() + " SYMBOL TABLE ===\n");

    // Member variables
    string.append(">>> Member Variables:\n");
    for (MemberDescriptor member : members) string.append(member).append('\n');

    // Member methods
    string.append("\n>>> Member Methods:\n");
    for (JMMMethodDescriptor method : methods) string.append(method).append('\n');

    // Main
    if (jmmClass.hasMain()) string.append('\n').append(jmmClass.getMain()).append('\n');

    // Local tables
    string.append("\n>>> Function Locals Tables:\n");
    for (FunctionLocals locals : methodLocalsMap.values()) string.append(locals);

    return string.append('\n').toString();
  }

  void dump() {
    System.out.print(toString());
  }
}
