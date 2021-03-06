package compiler.dag;

import compiler.symbols.LocalDescriptor;

public class DAGLocal extends DAGVariable implements LocalVariable {
  int localTableIndex = -1;

  DAGLocal(LocalDescriptor var) {
    super(var);
  }

  @Override
  public LocalDescriptor getVariable() {
    return (LocalDescriptor) var;
  }

  @Override
  public int getIndex() {
    return localTableIndex;
  }

  /**
   * Set the index in the local variables table for this local variable.
   * This can be called multiple times.
   */
  public void setIndex(int index) {
    assert index >= -1;
    this.localTableIndex = index;
  }

  /**
   * @return True if this variable has been set a local table index.
   */
  public boolean hasIndex() {
    return localTableIndex >= 0;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "{local " + var.getType() + " " + var.getName() + "}";
  }
}
