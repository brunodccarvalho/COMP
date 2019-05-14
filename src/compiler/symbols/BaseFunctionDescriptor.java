package compiler.symbols;

import java.util.Objects;

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

  @Override
  public String getBytecodeString() {
    return classDescriptor.getBytecodeString() + "/" + getBytecodeStringWithoutClass();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(classDescriptor, functionName);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof BaseFunctionDescriptor)) return false;
    BaseFunctionDescriptor other = (BaseFunctionDescriptor) obj;
    return Objects.equals(classDescriptor, other.classDescriptor)
        && Objects.equals(functionName, other.functionName);
  }
}
