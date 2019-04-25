package compiler.dag;

public class DAGBinaryOp extends DAGExpression {
  private BinaryOperator op;
  private DAGExpression lhs;
  private DAGExpression rhs;
}
