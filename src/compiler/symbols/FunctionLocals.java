package compiler.symbols;

import java.util.HashMap;

/**
 * A symbol table of declared function local variables. This table is associated
 * with a function (descriptor) and holds a map of names to local variable
 * descriptors. It can resolve names up the hierarchy (parameters, class
 * members...).
 */
public class FunctionLocals extends Descriptor {
  private final HashMap<String, LocalVariableDescriptor> variables;
  private final FunctionDescriptor function;

  public FunctionLocals(FunctionDescriptor function) {
    assert function != null;
    this.variables = new HashMap<>();
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
