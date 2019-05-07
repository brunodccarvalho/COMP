package compiler.dag;

import static compiler.dag.BinaryOperator.*;

import java.util.Objects;

import compiler.symbols.PrimitiveDescriptor;

/**
 * A DAGNode base class for binary operations. It holds a binary operator instance, and two
 * DAGExpressions for the left hand side and right hand side expressions.
 *
 * DAGBinaryOp may be reused within the same containing expression.
 */
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

  /**
   * @return The binary operator.
   */
  public BinaryOperator getOperator() {
    return op;
  }

  /**
   * @return The left hand side DAGExpression.
   */
  public DAGExpression getLhs() {
    return lhs;
  }

  /**
   * @return The right hand side DAGExpression.
   */
  public DAGExpression getRhs() {
    return rhs;
  }

  /**
   * @return true if the operation is an arithmetic one (sum, sub, mul or div)
   */
  public boolean isArithmetic() {
    return op == SUM || op == SUB || op == MUL || op == DIV;
  }

  /**
   * @return true if the operation is a comparison (less than only)
   */
  public boolean isComparison() {
    return op == LT;
  }

  /**
   * @return The type of each of the binary operation's operands.
   */
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
    String l = lhs instanceof DAGBinaryOp ? "(" + lhs + ")" : lhs.toString();
    String r = rhs instanceof DAGBinaryOp ? "(" + rhs + ")" : rhs.toString();
    return l + " " + op + " " + r;
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
