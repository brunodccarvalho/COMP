package compiler.dag;

import java.util.Objects;

import compiler.symbols.PrimitiveDescriptor;

/**
 * Not used. To be taken into consideration.
 */
public class DAGCondition {
  final DAGExpression condition;

  DAGCondition(DAGExpression condition) {
    assert condition != null;
    this.condition = condition;
  }

  public DAGExpression getExpression() {
    return condition;
  }

  public PrimitiveDescriptor getType() {
    return PrimitiveDescriptor.booleanDescriptor;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return condition.toString();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(condition);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof DAGCondition)) return false;
    DAGCondition other = (DAGCondition) obj;
    return Objects.equals(condition, other.condition);
  }
}
