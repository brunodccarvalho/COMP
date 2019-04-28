package compiler.symbols;

public interface Function {
  /**
   * @return The name of the function.
   */
  public String getName();

  /**
   * @return The class this function belongs to.
   */
  public ClassDescriptor getParentClass();

  /**
   * @return true if this function is static, false otherwise.
   */
  public boolean isStatic();

  /**
   * @return true if this function is a JMM function, false otherwise.
   */
  public boolean isJMM();

  /**
   * @return true if this function takes at least one argument.
   */
  public boolean hasParameters();

  /**
   * @return The number of parameters of this function.
   */
  public int getNumParameters();

  /**
   * @return The type descriptor for the return type of this function.
   */
  public TypeDescriptor getReturnType();
}
