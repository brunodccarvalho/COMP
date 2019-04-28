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
abstract class BaseFunctionDescriptor extends Descriptor implements Function {
  protected final ClassDescriptor classDescriptor;
  protected final String functionName;

  /**
   * Construct a new function with the given name.
   *
   * @param name The function's name
   */
  protected BaseFunctionDescriptor(String name, ClassDescriptor parent) {
    assert name != null && parent != null;
    this.functionName = name;
    this.classDescriptor = parent;
  }

  @Override
  public String getName() {
    return functionName;
  }

  @Override
  public ClassDescriptor getParentClass() {
    return classDescriptor;
  }

  @Override
  public String toString() {
    return functionName + "(...)";
  }
}
