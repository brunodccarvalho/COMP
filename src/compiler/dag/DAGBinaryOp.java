package compiler.dag;

import static compiler.dag.BinaryOperator.*;

import compiler.symbols.TypeDescriptor;
import jjt.SimpleNode;

public class DAGBinaryOp extends DAGExpression {
  private final BinaryOperator op;
  private final DAGExpression lhs;
  private final DAGExpression rhs;

  DAGBinaryOp(BinaryOp op, DAGExpression lhs, DAGExpression rhs) {
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

  public TypeDescriptor getOperandType() {
    return op.getOperandType();
  }

  @Override
  public TypeDescriptor getType() {
    return op.getExpressionType();
  }
}
