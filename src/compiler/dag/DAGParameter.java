package compiler.dag;

import compiler.symbols.ParameterDescriptor;

public class DAGParameter extends DAGVariable implements LocalVariable {
  DAGParameter(ParameterDescriptor var) {
    super(var);
  }

  @Override
  public ParameterDescriptor getVariable() {
    return (ParameterDescriptor) var;
  }

  public int getIndex() {
    return getVariable().getIndex() + 1;
  }
}
