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

  public VariableDescriptor getVariable() {
    return var;
  }

  @Override
  public TypeDescriptor getType() {
    return var.getType();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return var.toString();
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
