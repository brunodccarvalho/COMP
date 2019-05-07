package compiler.dag;

import java.util.Objects;

import compiler.symbols.TypeDescriptor;
import compiler.symbols.VariableDescriptor;

public class DAGVariable extends DAGExpression {
  protected final VariableDescriptor var;

  DAGVariable(VariableDescriptor var) {
    assert var != null;
    this.var = var;
  }

  // Dummy variable constructor.
  DAGVariable() {
    this.var = null;
  }

  // Internal method for ExpressionFactory and AssignmentFactory.
  boolean isDummy() {
    return var == null;
  }

  /**
   * @return The VariableDescriptor instance in the SymbolsTable for this variable.
   */
  public VariableDescriptor getVariable() {
    assert var != null;
    return var;
  }

  @Override
  public TypeDescriptor getType() {
    return var == null ? null : var.getType();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return var.getName();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(var);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof DAGVariable)) return false;
    DAGVariable other = (DAGVariable) obj;
    return Objects.equals(var, other.var);
  }
}
