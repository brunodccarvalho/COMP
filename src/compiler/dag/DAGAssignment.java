package compiler.dag;

import static compiler.symbols.TypeDescriptor.typematch;

import java.util.Objects;

import compiler.symbols.TypeDescriptor;

/**
 * A DAG node for assignments to variables. Because JMM does not allow assignments within
 * expressions, a DAGAssignments does not extend DAGExpression, although it theoretically should.
 *
 * The variable always has a resolved type; the expression however might have an unknown type.
 * Hence the type of the variable is the type of the assignment.
 */
public class DAGAssignment extends DAGNode {
  protected final DAGVariable assignVariable;
  protected final DAGExpression assignedExpression;

  public DAGAssignment(DAGVariable assignVariable, DAGExpression assignedExpression) {
    this.assignVariable = assignVariable;
    this.assignedExpression = assignedExpression;
  }

  /**
   * @return the DAGVariable node representing the assigned to variable.
   */
  public DAGVariable getVariable() {
    return this.assignVariable;
  }

  /**
   * @return the DAGExpression node, the value of which will be assigned to the variable.
   */
  public DAGExpression getExpression() {
    return this.assignedExpression;
  }

  /**
   * @return the type of the assignment, which is the type of the assigned to variable.
   */
  public TypeDescriptor getType() {
    assert typematch(assignVariable.getType(), assignedExpression.getType());
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

  /**
   * Distinct DAGAssignment instances never compare equal, and cannot be reused.
   */
  @Override
  public boolean equals(Object obj) {
    return obj instanceof DAGAssignment && this == obj;
  }
}
