package compiler.dag;

import static jjt.jmmTreeConstants.JJTAND;
import static jjt.jmmTreeConstants.JJTDIV;
import static jjt.jmmTreeConstants.JJTLT;
import static jjt.jmmTreeConstants.JJTMUL;
import static jjt.jmmTreeConstants.JJTSUB;
import static jjt.jmmTreeConstants.JJTSUM;

import compiler.symbols.TypeDescriptor;
import jjt.SimpleNode;

public class DAGBinaryOp extends DAGExpression {
  private final DAGExpression parent;
  private final SimpleNode node;

  private final BinaryOperator op;
  private final DAGExpression lhs;
  private final DAGExpression rhs;

  DAGBinaryOp(DAGExpression parent, SimpleNode node) {
    assert node != null;
    this.parent = parent;
    this.node = node;

    assert node.is(JJTAND) || node.is(JJTLT) || node.is(JJTSUM) || node.is(JJTSUB)
        || node.is(JJTMUL) || node.is(JJTDIV);

    switch (node.getId()) {
    case JJTAND:
      this.op = BinaryOperator.AND;
      break;
    case JJTLT:
      this.op = BinaryOperator.LT;
      break;
    case JJTSUM:
      this.op = BinaryOperator.SUM;
      break;
    case JJTSUB:
      this.op = BinaryOperator.SUB;
      break;
    case JJTMUL:
      this.op = BinaryOperator.MUL;
      break;
    case JJTDIV:
      this.op = BinaryOperator.DIV;
      break;
    }

    SimpleNode lhsNode = node.jjtGetChild(0);
    SimpleNode rhsNode = node.jjtGetChild(1);

    lhsNode.getId();
  }

  @Override
  public boolean hasSemanticError() {
    return false;
  }

  @Override
  public TypeDescriptor getType() {
    return op.getExpressionType();
  }
}
