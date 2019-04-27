package compiler.dag;

import java.util.Objects;

import compiler.symbols.PrimitiveDescriptor;

public class DAGNot extends DAGExpression {
  protected DAGExpression expression;

  DAGNot(DAGExpression expression) {
    this.expression = expression;
  }

  public DAGExpression getExpression() {
    return expression;
  }

  @Override
  public PrimitiveDescriptor getType() {
    return PrimitiveDescriptor.booleanDescriptor;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return (expression instanceof DAGBinaryOp) ? "!(" + expression + ")" : "!" + expression;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(expression);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof DAGNot)) return false;
    DAGNot other = (DAGNot) obj;
    return Objects.equals(expression, other.expression);
  }
}
