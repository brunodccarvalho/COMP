package compiler.dag;

import compiler.symbols.ClassDescriptor;
import compiler.symbols.ThisDescriptor;

public class DAGThis extends DAGVariable {
  DAGThis(ThisDescriptor thisVariable) {
    super(thisVariable);
  }

  public ThisDescriptor getVariable() {
    return (ThisDescriptor) var;
  }

  @Override
  public ClassDescriptor getType() {
    return ((ThisDescriptor) var).getType();
  }
}
