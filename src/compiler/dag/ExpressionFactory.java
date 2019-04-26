package compiler.dag;

import static jjt.jmmTreeConstants.*;

import java.util.HashSet;

import compiler.modules.CompilerModule;
import compiler.symbols.FunctionLocals;
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
  private HashSet<DAGExpression> expressionSet = new HashSet<>();

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

  private DAGIntegerConstant buildInteger(SimpleNode node) {
    assert node.is(JJTINTEGER);

    String intString = node.jjtGetVal();

    try {
      int constant = Integer.parseInt(intString);
      return new DAGIntegerConstant(constant);
    } catch (NumberFormatException e) {
      // ERROR: Integer literal constant value is not representable.
      System.err.println("The literal " + intString + " of type int is out of range");
      status(MINOR_ERRORS);
    }

    return null;
  }

  private DAGVariable buildVariable(SimpleNode node) {
    assert node.is(JJTIDENTIFIER);
    return null;
  }

  private DAGBooleanConstant buildBoolean(SimpleNode node) {
    return null;
  }

  private DAGThis buildThis(SimpleNode node) {
    return null;
  }

  private DAGNewIntArray buildNewIntArray(SimpleNode node) {
    return null;
  }

  private DAGNewClass buildNewClass(SimpleNode node) {
    return null;
  }

  private DAGLength buildLength(SimpleNode node) {
    return null;
  }

  private DAGUnaryOp buildUnaryOp(SimpleNode node) {
    return null;
  }

  private DAGBinaryOp buildBinaryOp(SimpleNode node) {
    return null;
  }

  private DAGBracket buildBracket(SimpleNode node) {
    return null;
  }

  private DAGCall buildCall(SimpleNode node) {
    return null;
  }
}
