package compiler.dag;

import static jjt.jmmTreeConstants.*;
import static compiler.symbols.PrimitiveDescriptor.*;

import java.util.HashMap;

import compiler.FunctionSignature;
import compiler.symbols.ClassDescriptor;
import compiler.symbols.FunctionLocals;
import compiler.symbols.LocalDescriptor;
import compiler.symbols.TypeDescriptor;
import compiler.symbols.VariableDescriptor;

import jjt.SimpleNode;

/**
 * A Factory class that manages the construction of one DAGExpression when a JJT
 * Expression is found in the Abstract Syntax Tree. It knows how to instantiate
 * all the possible subclasses of DAGExpression for each possible type of AST node.
 *
 * If more Expressions are found within the initial JJT Expression (naturally) no more are created.
 */
public class ExpressionFactory extends BaseDAGFactory {
  /**
   * Set of DAG expressions already constructed.
   */
  private HashMap<DAGExpression, DAGExpression> cache = new HashMap<>();

  private FunctionLocals locals;

  /**
   * @param locals The table of locals variables.
   */
  public ExpressionFactory(FunctionLocals locals) {
    super(locals);
  }

  /**
   * Construct a new DAGExpression for this SimpleNode. It is possible for an equivalent
   * DAGExpression to exist in the expressionSet; deciding whether to use the equivalent object or
   * the newly created one is done elsewhere.
   *
   * @param node The AST's SimpleNode object.
   * @return The DAGExpression node.
   */
  @Override
  public DAGExpression build(SimpleNode node) {
    // ... common pre-build

    // Forward the build to the appropriate build function.
    switch (node.getId()) {
    case JJTINTEGER:
      return buildInteger(node);
    case JJTTRUE:
    case JJTFALSE:
      return buildBoolean(node);
    case JJTIDENTIFIER:
      return buildVariable(node);
    case JJTTHIS:
      return buildThis(node);
    case JJTNEWINTARRAY:
      return buildNewIntArray(node);
    case JJTNEWCLASS:
      return buildNewClass(node);
    case JJTLENGTH:
      return buildLength(node);
    case JJTNOT:
      return buildUnaryOp(node);
    case JJTAND:
    case JJTLT:
    case JJTSUM:
    case JJTSUB:
    case JJTMUL:
    case JJTDIV:
      return buildBinaryOp(node);
    case JJTBRACKET:
      return buildBracket(node);
    case JJTCALL:
      return buildCall(node);
    }

    // ... common post-build

    // We should never arrive here
    assert false;
    return null;
  }

  private DAGExpression reuse(DAGExpression dagNode) {
    if (cache.containsKey(dagNode)) {
      return cache.get(dagNode);
    } else {
      cache.put(dagNode, dagNode);
      return dagNode;
    }
  }

  /**
   * @SemanticError: Integer literal constant value is not representable.
   *
   * @param node A JJT node holding an integer literal constant
   * @return A new DAGIntegerConstant holding the constant.
   */
  private DAGIntegerConstant buildInteger(SimpleNode node) {
    assert node.is(JJTINTEGER);

    String intString = node.jjtGetVal();

    try {
      int constant = Integer.parseInt(intString);
      return new DAGIntegerConstant(constant);
    }
    // ERROR: Integer literal constant value is not representable.
    catch (NumberFormatException e) {
      System.err.println("The literal " + intString + " of type int is out of range");
      status(MINOR_ERRORS);
      return new DAGIntegerConstant(1);
    }
  }

  /**
   * @param node A JJT node holding a true or false boolean literal
   * @return A new DAGBooleanConstant holding the constant.
   */
  private DAGBooleanConstant buildBoolean(SimpleNode node) {
    assert node.is(JJTTRUE) || node.is(JJTFALSE);

    if (node.is(JJTTRUE)) {
      return new DAGBooleanConstant(true);
    } else {
      return new DAGBooleanConstant(false);
    }
  }

  /**
   * @SemanticError: Variable name cannot be resolved to a variable.
   *
   * @param node A JJT node holding a variable identifier
   * @return A new DAGVariable holding the variable's descriptor.
   */
  private DAGVariable buildVariable(SimpleNode node) {
    assert node.is(JJTIDENTIFIER);

    String varName = node.jjtGetVal();
    VariableDescriptor var = locals.resolve(varName);

    // ERROR: $variablename cannot be resolved to a variable.
    if (var == null) {
      System.err.println(varName + " cannot be resolved to a variable");
      status(MINOR_ERRORS);

      // TODO: Handle UnresolvedVariableName error continuation
      return null;
    }

    return new DAGVariable(var);
  }

  /**
   * @SemanticError: Cannot use this in a static context.
   *
   * @param node A JJT node holding a this literal keyword
   * @return A new DAGThis node.
   */
  private DAGThis buildThis(SimpleNode node) {
    assert node.is(JJTTHIS);

    LocalDescriptor thisVar = locals.getThis();

    // ERROR: Cannot use this in a static context.
    if (thisVar == null) {
      System.err.println("Cannot use this in a static context");
      status(MAJOR_ERRORS);
      return null;
    }

    return new DAGThis(thisVar);
  }

  /**
   * @SemanticError: Type mismatch: expected int, but found X.
   *
   * @param node A JJT node representing a new int array declaration.
   * @return A new DAGNewIntArray node.
   */
  private DAGNewIntArray buildNewIntArray(SimpleNode node) {
    assert node.is(JJTNEWINTARRAY);

    SimpleNode indexExpressionNode = node.jjtGetChild(0);
    DAGExpression expression = reuse(build(indexExpressionNode));

    // ERROR: Type mismatch: expected int, but found X.
    if (intDescriptor != expression.getType()) {
      System.err.println("Type mismatch: expected int type, but found " + expression.getType());
      status(MINOR_ERRORS);
    }

    return new DAGNewIntArray(expression);
  }

  /**
   * No semantic errors.
   *
   * @param node A JJT node representing a new declaration for a class type, with no args.
   * @return A new DAGNewClass node.
   */
  private DAGNewClass buildNewClass(SimpleNode node) {
    assert node.is(JJTCLASSTYPE);

    SimpleNode classTypeNode = node.jjtGetChild(0);
    assert classTypeNode.is(JJTCLASSTYPE);

    String className = classTypeNode.jjtGetVal();
    ClassDescriptor classDescriptor = (ClassDescriptor) TypeDescriptor.getOrCreate(className);

    return new DAGNewClass(classDescriptor);
  }

  /**
   * @SemanticError: Type mismatch: expected int[], but found X.
   *
   * @param node A JJT node representing access to a property called 'length' of an int array.
   * @return A new DAGLength node.
   */
  private DAGLength buildLength(SimpleNode node) {
    assert node.is(JJTLENGTH);

    SimpleNode expressionNode = node.jjtGetChild(0);
    DAGExpression expression = reuse(build(expressionNode));

    // ERROR: Type mismatch: expected int[], but found X.
    if (intArrayDescriptor != expression.getType()) {
      System.err.println("Type mismatch: expected int[] type, but found " + expression.getType());
      status(MINOR_ERRORS);
    }

    return new DAGLength(expression);
  }

  /**
   * @SemanticError: Type mismatch: expected boolean, but found X.
   *
   * @param node A JJT node representing a unary operation. Only ! is supported.
   * @return A new DAGNot node.
   */
  private DAGNot buildUnaryOp(SimpleNode node) {
    assert node.is(JJTNOT);

    SimpleNode expressionNode = node.jjtGetChild(0);
    DAGExpression expression = build(expressionNode);

    // ERROR: Type mismatch: expected boolean, but found X.
    if (booleanDescriptor != expression.getType()) {
      System.err.println("Type mismatch: expected boolean type, but found " + expression.getType());
      status(MINOR_ERRORS);
    }

    return new DAGNot(expression);
  }

  /**
   * @SemanticError: Type mismatch: expected [int, boolean], but found X.
   *
   * @param node A JJT node representing a binary operation. Only ! is supported.
   * @return A new DAGBinaryOp node, if not optimized.
   */
  private DAGExpression buildBinaryOp(SimpleNode node) {
    assert node.is(JJTAND) || node.is(JJTLT) || node.is(JJTSUM) || node.is(JJTSUB)
        || node.is(JJTMUL) || node.is(JJTDIV);

    SimpleNode lhsNode = node.jjtGetChild(0);
    SimpleNode rhsNode = node.jjtGetChild(1);

    DAGExpression lhs = reuse(build(lhsNode));
    DAGExpression rhs = reuse(build(rhsNode));
    BinaryOperator op = BinaryOperator.from(node);

    // ERROR: Type mismatch in the lhs.
    if (lhs.getType() != op.getOperandType()) {
      System.err.println("Type mismatch: expected " + op.getOperandType() + " type, but found "
                         + lhs.getType());
      status(MINOR_ERRORS);
    }

    // ERROR: Type mismatch in the rhs.
    if (rhs.getType() != op.getOperandType()) {
      System.err.println("Type mismatch: expected " + op.getOperandType() + " type, but found "
                         + rhs.getType());
      status(MINOR_ERRORS);
    }

    return new DAGBinaryOp(op, lhs, rhs);
  }

  /**
   * @SemanticError: Type mismatch: Expected int[] array-type, but found X.
   * @SemanticError: Type mismatch: Expected int type for bracket index, but found X.
   *
   * @param node A JJT Bracket node representing an array access.
   * @return A possibly reused DAGBracket node.
   */
  private DAGBracket buildBracket(SimpleNode node) {
    assert node.is(JJTBRACKET);

    SimpleNode arrayNode = node.jjtGetChild(0);
    SimpleNode indexNode = node.jjtGetChild(1);

    DAGExpression array = reuse(build(arrayNode));
    DAGExpression index = reuse(build(indexNode));

    // ERROR: Type mismatch in the array expression.
    if (intArrayDescriptor != array.getType()) {
      System.err.println("Type mismatch: expected int[] array-type, but found " + array.getType());
      status(MINOR_ERRORS);
    }

    // ERROR: Type mismatch in the index expression.
    if (intDescriptor != index.getType()) {
      System.err.println("Type mismatch: expected int type, but found " + index.getType());
      status(MINOR_ERRORS);
    }

    return new DAGBracket(array, index);
  }

  /**
   * @SemanticError: Expression E is not an object.
   * @SemanticError: The method M is undefined for the type T.
   * @SemanticError: No overload of method M for type T matches the signature S.
   *
   * @param node A JJT function call node, either member or static method.
   * @return A new DAGCall node.
   */
  private DAGCall buildCall(SimpleNode node) {
    assert node.is(JJTCALL);

    // * Need to differentiate between static and non-static method calls.
    // If the objectNode is a JJT Identifier node, it might be the name
    // of a variable or the name of a class. Variables take precedence.
    // A class name is otherwise assumed.

    SimpleNode objectNode = node.jjtGetChild(0);

    if (objectNode.is(JJTIDENTIFIER)) {
      String identifier = objectNode.jjtGetVal();

      // Failed to resolve variable name.
      if (locals.resolve(identifier) == null) {
        return buildStaticCall(node);
      }
    }

    return buildMethodCall(node);
  }

  /**
   * @SemanticError: The method M is undefined for the type T.
   * @SemanticError: No overload of method M for type T matches the signature S.
   *
   * @param node A JJT static method call node.
   * @return A new DAGStaticCall node.
   */
  private DAGStaticCall buildStaticCall(SimpleNode node) {
    assert node.is(JJTCALL);

    SimpleNode classNode = node.jjtGetChild(0);
    SimpleNode methodNameNode = node.jjtGetChild(1);
    SimpleNode argumentListNode = node.jjtGetChild(2);
    assert classNode.is(JJTIDENTIFIER);
    assert methodNameNode.is(JJTMETHODNAME);
    assert argumentListNode.is(JJTARGUMENTLIST);

    String className = classNode.jjtGetVal();
    ClassDescriptor classDescriptor = (ClassDescriptor) TypeDescriptor.getOrCreate(className);

    String methodName = methodNameNode.jjtGetVal();

    int numArguments = argumentListNode.jjtGetNumChildren();
    DAGExpression[] arguments = new DAGExpression[numArguments];
    TypeDescriptor[] types = new TypeDescriptor[numArguments];

    for (int i = 0; i < numArguments; ++i) {
      arguments[i] = reuse(build(argumentListNode.jjtGetChild(i)));
      types[i] = arguments[i].getType();
    }

    FunctionSignature signature = new FunctionSignature(types);

    // * Write the most generic error possible.
    // ERROR: The method M is undefined for type T.
    if (!classDescriptor.hasStaticMethod(methodName)) {
      System.err.println("Static method " + methodName + " is undefined for class "
                         + classDescriptor);
      status(MAJOR_ERRORS);
    }
    // ERROR: No method M for type T matches the signature S.
    else if (!classDescriptor.hasStaticMethod(methodName, signature)) {
      System.err.println("No static method " + methodName + " for class " + classDescriptor
                         + " matches the signature " + methodName + signature);
      status(MAJOR_ERRORS);
    }

    return new DAGStaticCall(classDescriptor, methodName, signature, arguments);
  }

  /**
   * @SemanticError: Expression E is not an object.
   * @SemanticError: The method M is undefined for the type T.
   * @SemanticError: No overload of method M for type T matches the signature S.
   *
   * @param node A JJT member method call node.
   * @return A new DAGMethodCall node.
   */
  private DAGMethodCall buildMethodCall(SimpleNode node) {
    assert node.is(JJTCALL);

    SimpleNode objectNode = node.jjtGetChild(0);
    SimpleNode methodNameNode = node.jjtGetChild(1);
    SimpleNode argumentListNode = node.jjtGetChild(2);
    assert methodNameNode.is(JJTMETHODNAME);
    assert argumentListNode.is(JJTARGUMENTLIST);

    DAGExpression object = reuse(build(objectNode));

    String methodName = methodNameNode.jjtGetVal();

    int numArguments = argumentListNode.jjtGetNumChildren();
    DAGExpression[] arguments = new DAGExpression[numArguments];
    TypeDescriptor[] types = new TypeDescriptor[numArguments];

    for (int i = 0; i < numArguments; ++i) {
      arguments[i] = reuse(build(argumentListNode.jjtGetChild(i)));
      types[i] = arguments[i].getType();
    }

    FunctionSignature signature = new FunctionSignature(types);

    // * Write the most generic error possible.
    // ERROR: Object expression is not of class type.
    if (!object.getType().isClass()) {
      System.err.println("Object is not of class type");
      status(MAJOR_ERRORS);
    } else {
      ClassDescriptor objectClass = (ClassDescriptor) object.getType();

      // ERROR: The method M is undefined for type T.
      if (!objectClass.hasMethod(methodName)) {
        System.err.println("Method " + methodName + " is undefined for type " + objectClass);
        status(MAJOR_ERRORS);
      }
      // ERROR: No method M for type T matches the signature S.
      else if (!objectClass.hasMethod(methodName, signature)) {
        System.err.println("No method " + methodName + " for type " + objectClass
                           + " matches the signature " + methodName + signature);
        status(MAJOR_ERRORS);
      }
    }

    return new DAGMethodCall(object, methodName, signature, arguments);
  }
}
