package compiler.dag;

import compiler.symbols.TypeDescriptor;
import compiler.symbols.VariableDescriptor;

public class DAGVariable extends DAGExpression {
  private VariableDescriptor var;

  DAGVariable(VariableDescriptor var) {
    this.var = var;
  }

  public VariableDescriptor getVariable() {
    return var;
  }

  @Override
  public TypeDescriptor getType() {
    return var.getType();
  }
}
