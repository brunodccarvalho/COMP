package compiler.symbols;

public interface ResolverClass {
  public class Deduction {
    private final CallableDescriptor callable;
    private final boolean success, multiple;

    public Deduction(CallableDescriptor callable, boolean success, boolean multiple) {
      this.callable = callable;
      this.success = success;
      this.multiple = multiple;
    }

    public CallableDescriptor getCallable() {
      return callable;
    }

    public boolean wasSuccess() {
      return success;
    }

    public boolean wereMultiple() {
      return multiple;
    }
  }

  /**
   * @param name A method's name
   * @return true if this class has at least one member method identified by name.
   */
  public abstract boolean hasMethod(String name);

  /**
   * @param name      A method's name
   * @param signature A method's parsed signature
   * @return true if this class has at least one member method identified by name
   *         and matching the corresponding signature.
   */
  public abstract boolean hasMethod(String name, FunctionSignature signature);

  /**
   * @param name       A method's name
   * @param signature  A method's parsed signature
   * @param returnType A method's deduced return type
   * @return true if this class has at least one member method identified by name
   *         and matching the corresponding signature and returning the specified
   *         type.
   */
  public abstract boolean hasReturning(String name, FunctionSignature signature,
                                       TypeDescriptor returnType);

  /**
   * @param name A static method's name
   * @return true if this class has at least one static method identified by name.
   */
  public abstract boolean hasStaticMethod(String name);

  /**
   * @param name      A static method's name
   * @param signature A static method's parsed signature
   * @return true if this class has at least one static method identified by name
   *         and matching the corresponding signature.
   */
  public abstract boolean hasStaticMethod(String name, FunctionSignature signature);

  /**
   * @param name       A static method's name
   * @param signature  A static method's parsed signature
   * @param returnType A static method's deduced return type
   * @return true if this class has at least one static method identified by name
   *         and matching the corresponding signature and returning the specified
   *         type.
   */
  public abstract boolean hasStaticReturning(String name, FunctionSignature signature,
                                             TypeDescriptor returnType);

  /**
   * @param name The variable name to be resolved non-statically
   * @return The variable descriptor for the given name, or null if not found.
   */
  public abstract VariableDescriptor resolve(String name);

  /**
   * @param name The variable name to be resolved statically
   * @return The variable descriptor for the given name, or null if not found.
   */
  public abstract VariableDescriptor resolveStatic(String name);

  /**
   * @param name      The name of a method
   * @param signature The signature of the method
   * @return The return type of that method.
   */
  public abstract TypeDescriptor getReturnType(String name, FunctionSignature signature);

  /**
   * @param name The name of a static method
   * @param signature The signature of the method
   * @return The return type of that method.
   */
  public abstract TypeDescriptor getReturnTypeStatic(String name, FunctionSignature signature);

  /**
   * @param name       A method's name
   * @param signature  A method's original signature, augmented
   * @return The function matching this member invocation, without unknown types in it.
   */
  public abstract Deduction deduce(String name, FunctionSignature signature);

  /**
   * @param name       A static method's name
   * @param signature  A static method's original signature, augmented
   * @return The function matching this static invocation, without unknown types in it.
   */
  public abstract Deduction deduceStatic(String name, FunctionSignature signature);
}
