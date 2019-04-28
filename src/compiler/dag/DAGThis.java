package compiler.dag;

import compiler.symbols.JMMClassDescriptor;
import compiler.symbols.ThisDescriptor;

public class DAGThis extends DAGVariable {
  DAGThis(ThisDescriptor thisVariable) {
    super(thisVariable);
  }

  public ThisDescriptor getVariable() {
    return (ThisDescriptor) var;
  }

  @Override
  public JMMClassDescriptor getType() {
    return ((ThisDescriptor) var).getType();
  }
}
