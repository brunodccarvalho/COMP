package compiler.dag;

import compiler.symbols.LocalDescriptor;

public class DAGLocal extends DAGVariable implements LocalVariable {
  protected int localTableIndex;

  DAGLocal(LocalDescriptor var) {
    super(var);
  }

  @Override
  public LocalDescriptor getVariable() {
    return (LocalDescriptor) var;
  }

  public int getIndex() {
    return localTableIndex;
  }

  public void setIndex(int index) {
    this.localTableIndex = index;
  }
}
