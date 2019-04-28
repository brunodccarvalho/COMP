package compiler.symbols;

public interface JMMFunction extends Function {
  /**
   * @return The class this function belongs to.
   */
  public JMMClassDescriptor getParentClass();

  /**
   * @param name The name of a parameter
   * @return true if this JMM function has parameter 'name'.
   */
  public boolean hasParameter(String name);

  /**
   * @param name The name of a parameter
   * @return The descriptor for the parameter 'name'.
   */
  public ParameterDescriptor getParameter(String name);

  /**
   * @param name The name of a parameter
   * @return The type descriptor for the parameter 'name'.
   */
  public TypeDescriptor getParameterType(String name);

  /**
   * @param name The name of an identifier.
   * @return Resolved variable descriptor for 'name', which will either be null, a parameter
   *     descriptor or member descriptor.
   */
  public VariableDescriptor resolve(String name);
}
