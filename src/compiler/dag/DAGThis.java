package compiler.dag;

import compiler.symbols.JMMClassDescriptor;
import compiler.symbols.ThisDescriptor;

public class DAGThis extends DAGVariable implements LocalVariable {
  DAGThis(ThisDescriptor thisVariable) {
    super(thisVariable);
  }

  @Override
  public ThisDescriptor getVariable() {
    return (ThisDescriptor) var;
  }

  /**
   * @return The index of this variable in the local variables table.
   */
  public int getIndex() {
    return 0;
  }

  @Override
  public JMMClassDescriptor getType() {
    return ((ThisDescriptor) var).getType();
  }
}
