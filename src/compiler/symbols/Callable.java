package compiler.symbols;

import compiler.FunctionSignature;

public interface Callable extends Function {
  /**
   * @param index A parameter position
   * @return true if this function takes an argument at position index.
   */
  public boolean hasParameter(int index);

  /**
   * @param index A parameter position
   * @return The descriptor of the type of parameter at position index.
   */
  public TypeDescriptor getParameterType(int index);

  /**
   * @return An array with the parameter's type descriptors.
   */
  public TypeDescriptor[] getParameterTypes();

  /**
   * @return The function's signature.
   */
  public FunctionSignature getSignature();

  /**
   * @param deducedSignature A function signature found in semantic analysis
   * @return true if an invocation of a function with the given signature could be an invocation of
   *     this function, assuming all else matches (class, static, function name).
   */
  public boolean matches(FunctionSignature deducedSignature);

  /**
   * @param deducedReturnType A type descriptor found in semantic analysis
   * @return true if an invocation of this function could return the given type.
   */
  public boolean returns(TypeDescriptor deducedReturnType);

  /**
   * @param deducedSignature A function signature found in semantic analysis
   * @param deducedReturnType A type descriptor found in semantic analysis
   * @return true if an invocation of a function with the given signature could be an invocation of
   *     this function with the given return type, assuming all else matches (class, static,
   *     function name).
   */
  public boolean returning(FunctionSignature deducedSignature, TypeDescriptor deducedReturnType);
}
