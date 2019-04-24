package compiler.symbols;

import java.util.HashMap;

/**
 * A symbol table of declared function local variables. This table is associated
 * with a function (descriptor) and holds a map of names to local variable
 * descriptors. It can resolve names up the hierarchy (parameters, class
 * members...).
 */
public class FunctionLocals extends Descriptor {
  private final HashMap<String, LocalDescriptor> variables;
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
  public LocalDescriptor getVariable(String name) {
    return variables.get(name);
  }

  /**
   * Adds a new local variable to the table.
   */
  public void addVariable(LocalDescriptor var) {
    assert !variables.containsKey(var.getName()) && !function.hasParameter(var.getName());
    variables.put(var.getName(), var);
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
    LocalDescriptor variable = variables.get(name);
    if (variable != null)
      return variable;
    else
      return function.resolve(name);
  }

  @Override
  public String toString() {
    StringBuilder string = new StringBuilder();

    string.append("## Function Locals of " + function + "\n");
    for (LocalDescriptor local : variables.values()) {
      string.append("  ").append(local).append('\n');
    }

    return string.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((function == null) ? 0 : function.hashCode());
    result = prime * result + ((variables == null) ? 0 : variables.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    FunctionLocals other = (FunctionLocals) obj;
    if (function == null) {
      if (other.function != null)
        return false;
    } else if (!function.equals(other.function))
      return false;
    if (variables == null) {
      if (other.variables != null)
        return false;
    } else if (!variables.equals(other.variables))
      return false;
    return true;
  }
}
