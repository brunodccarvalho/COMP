package compiler.symbols;

import java.util.HashMap;

public class FunctionLocals extends Descriptor {
  private HashMap<String, LocalVariableDescriptor> variables;
  private FunctionDescriptor function;

  public FunctionLocals(FunctionDescriptor function) {
    assert function != null;
    this.function = function;
  }

  public boolean hasVariable(String name) {
    return variables.containsKey(name);
  }

  public LocalVariableDescriptor getVariable(String name) {
    return variables.get(name);
  }

  public boolean addVariable(LocalVariableDescriptor var) {
    return variables.putIfAbsent(var.getName(), var) == null;
  }

  public FunctionDescriptor getFunction() {
    return function;
  }

  public Descriptor resolve(String name) {
    LocalVariableDescriptor variable = variables.get(name);
    if (variable != null)
      return variable;
    else
      return function.resolve(name);
  }
}
