package compiler.dag;

import static compiler.dag.BinaryOperator.*;

import compiler.symbols.PrimitiveDescriptor;

public class DAGBinaryOp extends DAGExpression {
  protected final BinaryOperator op;
  protected final DAGExpression lhs;
  protected final DAGExpression rhs;

  DAGBinaryOp(BinaryOperator op, DAGExpression lhs, DAGExpression rhs) {
    this.op = op;
    this.lhs = lhs;
    this.rhs = rhs;
  }

  public BinaryOperator getOperator() {
    return op;
  }

  public DAGExpression getLhs() {
    return lhs;
  }

  public DAGExpression getRhs() {
    return rhs;
  }

  public boolean isArithmetic() {
    return op == SUM || op == SUB || op == MUL || op == DIV;
  }

  public boolean isComparison() {
    return op == LT;
  }

  public PrimitiveDescriptor getOperandType() {
    return op.getOperandType();
  }

  @Override
  public PrimitiveDescriptor getType() {
    return op.getExpressionType();
  }
}
