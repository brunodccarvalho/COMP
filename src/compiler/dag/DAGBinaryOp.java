package compiler.dag;

import static compiler.dag.BinaryOperator.*;

import java.util.Objects;

import compiler.symbols.PrimitiveDescriptor;

public class DAGBinaryOp extends DAGExpression {
  protected final BinaryOperator op;
  protected final DAGExpression lhs;
  protected final DAGExpression rhs;

  DAGBinaryOp(BinaryOperator op, DAGExpression lhs, DAGExpression rhs) {
    assert op != null && lhs != null && rhs != null;
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

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return lhs + " " + op + " " + rhs;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(lhs, op, rhs);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof DAGBinaryOp)) return false;
    DAGBinaryOp other = (DAGBinaryOp) obj;
    return Objects.equals(lhs, other.lhs) && op == other.op && Objects.equals(rhs, other.rhs);
  }
}
