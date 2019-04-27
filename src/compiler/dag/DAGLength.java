package compiler.dag;

import java.util.Objects;

import compiler.symbols.PrimitiveDescriptor;

public class DAGLength extends DAGExpression {
  protected DAGExpression expression;

  DAGLength(DAGExpression expression) {
    assert expression != null;
    this.expression = expression;
  }

  public DAGExpression getExpression() {
    return expression;
  }

  @Override
  public PrimitiveDescriptor getType() {
    return PrimitiveDescriptor.intDescriptor;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return expression + ".length";
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
    if (!(obj instanceof DAGLength)) return false;
    DAGLength other = (DAGLength) obj;
    return Objects.equals(expression, other.expression);
  }
}
