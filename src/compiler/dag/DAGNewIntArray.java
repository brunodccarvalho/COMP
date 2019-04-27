package compiler.dag;

import java.util.Objects;

import compiler.symbols.PrimitiveDescriptor;

public class DAGNewIntArray extends DAGNew {
  protected DAGExpression indexExpression;

  DAGNewIntArray(DAGExpression indexExpression) {
    this.indexExpression = indexExpression;
  }

  public DAGExpression getIndexExpression() {
    return indexExpression;
  }

  @Override
  public PrimitiveDescriptor getType() {
    return PrimitiveDescriptor.intArrayDescriptor;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "new int[" + indexExpression + "]";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(indexExpression);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof DAGNewIntArray)) return false;
    DAGNewIntArray other = (DAGNewIntArray) obj;
    return Objects.equals(indexExpression, other.indexExpression);
  }
}
