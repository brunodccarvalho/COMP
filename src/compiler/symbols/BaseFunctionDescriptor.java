package compiler.symbols;

/**
 * Base class for function descriptors. Simply holds the name of the function
 * and abstracts for the parameter names and number of parameters, but not their
 * descriptors.
 *
 * This allows integrating the extremely awkward JMM main function descriptor
 * into the hierarchy properly. It could warrant a different name though...
 *
 * Leave this package-private.
 */
abstract class BaseFunctionDescriptor extends Descriptor {
  protected String functionName;

  /**
   * Construct a new function with the given name.
   *
   * @param name The function's name
   */
  protected BaseFunctionDescriptor(String name) {
    assert name != null;
    this.functionName = name;
  }

  /**
   * @return The name of the function.
   */
  public String getName() {
    return functionName;
  }

  /**
   * @param name The name of the parameter
   * @return true if this function has a parameter with the given name.
   */
  public abstract boolean hasParameter(String name);

  /**
   * @return true if this function takes at least one argument.
   */
  public abstract boolean hasParameters();

  /**
   * @return The number of parameters of this function.
   */
  public abstract int getNumParameters();

  /**
   * @param name The variable name to be resolved
   * @return The variable descriptor for the given name, or null if not found.
   */
  public abstract VariableDescriptor resolve(String name);

  @Override
  public String toString() {
    return functionName + "(...)";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((functionName == null) ? 0 : functionName.hashCode());
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
    BaseFunctionDescriptor other = (BaseFunctionDescriptor) obj;
    if (functionName == null) {
      if (other.functionName != null)
        return false;
    } else if (!functionName.equals(other.functionName))
      return false;
    return true;
  }
}
