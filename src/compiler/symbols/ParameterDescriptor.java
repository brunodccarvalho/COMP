package compiler.symbols;

/**
 * A descriptor for a function's parameter variable.
 */
public class ParameterDescriptor extends VariableDescriptor {
  private final FunctionDescriptor function;
  private final int index;

  /**
   * Creates a new parameter descriptor for a function's parameter list. An
   * instance of this class can only be created by function descriptors.
   *
   * @param type     The parameter's type
   * @param name     The parameter's name
   * @param function The parameter's parent function
   * @param index    The parameter's position in the parameter list
   */
  ParameterDescriptor(TypeDescriptor type, String name, FunctionDescriptor function, int index) {
    super(type, name);
    assert function != null && index >= 0;
    this.function = function;
    this.index = index;
  }

  /**
   * @return The parameter's parent function
   */
  public FunctionDescriptor getFunction() {
    return function;
  }

  /**
   * @return The parameter's position in the parameter list
   */
  public int getIndex() {
    return index;
  }
}
