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

  /**
   * Creates a new (mutable) table of function local variables.
   *
   * @param function The encapsulating function.
   */
  public FunctionLocals(FunctionDescriptor function) {
    assert function != null;
    this.variables = new HashMap<>();
    this.function = function;
  }

  /**
   * Returns true if this function has a local with the given name.
   */
  public boolean hasVariable(String name) {
    return variables.containsKey(name);
  }

  /**
   * Returns the local variable descriptor for the given name.
   */
  public LocalVariableDescriptor getVariable(String name) {
    return variables.get(name);
  }

  /**
   * Adds a new local variable to the table.
   */
  public boolean addVariable(LocalVariableDescriptor var) {
    return variables.putIfAbsent(var.getName(), var) == null;
  }

  /**
   * Returns the encapsulation function.
   */
  public FunctionDescriptor getFunction() {
    return function;
  }

  /**
   * Resolve a given identifier within this local variables tables, or defer
   * resolution to the encapsulating function if there is no local variable with
   * the given name.
   */
  public Descriptor resolve(String name) {
    LocalVariableDescriptor variable = variables.get(name);
    if (variable != null)
      return variable;
    else
      return function.resolve(name);
  }
}
