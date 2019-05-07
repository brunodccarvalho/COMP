package compiler.dag;

import java.util.Objects;

public class DAGBracketAssignment extends DAGAssignment {
  protected final DAGExpression indexExpression;

  DAGBracketAssignment(DAGVariable assignVariable, DAGExpression assignedExpression,
                       DAGExpression indexExpression) {
    super(assignVariable, assignedExpression);
    this.indexExpression = indexExpression;
  }

  /**
   * @return The expression for the index of the assigned array variable.
   */
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
}
