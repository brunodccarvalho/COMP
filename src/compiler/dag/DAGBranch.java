package compiler.dag;

import java.util.Objects;

abstract class DAGBranch extends DAGNode {
  final DAGExpression condition;

  DAGBranch(DAGExpression condition) {
    assert condition != null;
    this.condition = condition;
  }

  /**
   * @return the DAGExpression node of the control flow's condition
   */
  public DAGExpression getConditionExpression() {
    return condition;
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
    if (!(obj instanceof DAGBranch)) return false;
    DAGBranch other = (DAGBranch) obj;
    return Objects.equals(condition, other.condition);
  }
}
