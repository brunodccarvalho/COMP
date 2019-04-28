package compiler.dag;

import java.util.Objects;

public class DAGBracketAssignment extends DAGAssignment {
  protected final DAGExpression indexExpression;

  public DAGBracketAssignment(DAGVariable assignVariable, DAGExpression assignedExpression,
                              DAGExpression indexExpression) {
    super(assignVariable, assignedExpression);
    this.indexExpression = indexExpression;
  }

  public DAGExpression getIndexExpression() {
    return this.indexExpression;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */

  @Override
  public String toString() {
    return assignVariable + "[" + indexExpression + "] = " + assignedExpression;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Objects.hash(indexExpression);
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!super.equals(obj)) return false;
    if (!(obj instanceof DAGBracketAssignment)) return false;
    DAGBracketAssignment other = (DAGBracketAssignment) obj;
    return Objects.equals(indexExpression, other.indexExpression);
  }
}
