package compiler.dag;

import java.util.Objects;

import compiler.symbols.TypeDescriptor;

public class DAGAssignment extends DAGNode {
  protected final DAGVariable assignVariable;
  protected final DAGExpression assignedExpression;

  public DAGAssignment(DAGVariable assignVariable, DAGExpression assignedExpression) {
    this.assignVariable = assignVariable;
    this.assignedExpression = assignedExpression;
  }

  public DAGVariable getVariable() {
    return this.assignVariable;
  }

  public DAGExpression getExpression() {
    return this.assignedExpression;
  }

  public TypeDescriptor getType() {
    assert assignVariable.getType() == assignedExpression.getType();
    return assignVariable.getType();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return assignVariable + " = " + assignedExpression;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(assignVariable, assignedExpression);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return obj instanceof DAGAssignment && this == obj;
  }
}
