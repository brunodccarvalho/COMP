package compiler.dag;

import static jjt.jmmTreeConstants.*;
import static compiler.symbols.PrimitiveDescriptor.*;

import java.util.HashMap;

import compiler.modules.CompilerModule;
import compiler.symbols.ClassDescriptor;
import compiler.symbols.FunctionLocals;
import compiler.symbols.LocalDescriptor;
import compiler.symbols.PrimitiveDescriptor;
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
public class ExpressionFactory extends CompilerModule {
  /**
   * Set of DAG expressions already constructed.
   */
  private HashMap<DAGExpression, DAGExpression> expressionSet = new HashMap<>();

  private FunctionLocals locals;
  private SimpleNode expressionNode;

  /**
   * @param locals The table of locals variables.
   * @param topExpressionNode An expression node found in the AST tree.
   */
  public ExpressionFactory(FunctionLocals locals, SimpleNode expressionNode) {
    assert locals != null && expressionNode != null;
    this.locals = locals;
    this.expressionNode = expressionNode;

    this.build(expressionNode);
  }

  /**
   * Construct a new DAGExpression for this SimpleNode. It is possible for an equivalent
   * DAGExpression to exist in the expressionSet; deciding whether to use the equivalent object or
   * the newly created one is done elsewhere.
   *
   * @param node The AST's SimpleNode object.
   * @return The DAGExpression node.
   */
  DAGExpression build(SimpleNode node) {
    // ... common pre-build

    // Forward the build to the appropriate build function.
    switch (node.getId()) {
    case JJTINTEGER:
      return buildInteger(node);
    case JJTIDENTIFIER:
      return buildVariable(node);
    case JJTTRUE:
    case JJTFALSE:
      return buildBoolean(node);
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

  // Reuse DAGExpression nodes
  private <DAGT extends DAGExpression> DAGT unduplicate(DAGT dagNode) {
    if (expressionSet.containsKey(dagNode)) {
      return (DAGT) expressionSet.get(dagNode);
    } else {
      expressionSet.put(dagNode, dagNode);
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

    // Build the DAGExpression.
    DAGExpression expression = build(indexExpressionNode);

    // Reuse the DAGExpression?
    if (expressionSet.containsKey(expression)) {
      expression = expressionSet.get(expression);
    } else {
      expressionSet.put(expression, expression);
    }

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

    // Build the DAGExpression.
    DAGExpression expression = build(expressionNode);

    // Reuse the DAGExpression?
    if (expressionSet.containsKey(expression)) {
      expression = expressionSet.get(expression);
    }

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

    // Build the DAGExpression.
    DAGExpression expression = build(expressionNode);

    // Reuse the DAGExpression?
    if (expressionSet.containsKey(expression)) {
      expression = expressionSet.get(expression);
    }

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

    DAGExpression lhs = build(lhsNode);

    // Reuse the DAGExpression?
    if (expressionSet.containsKey(lhs)) {
      lhs = expressionSet.get(lhs);
    }

    DAGExpression rhs = build(rhsNode);

    // Reuse the DAGExpression?
    if (expressionSet.containsKey(rhs)) {
      rhs = expressionSet.get(rhs);
    }
  }

  private DAGBracket buildBracket(SimpleNode node) {
    return null;
  }

  private DAGCall buildCall(SimpleNode node) {
    return null;
  }
}
