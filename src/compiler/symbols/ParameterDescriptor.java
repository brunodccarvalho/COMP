package compiler.symbols;

/**
 * A descriptor for a function's parameter variable.
 */
public class ParameterDescriptor extends VariableDescriptor {
  private final FunctionDescriptor function;
  private final int index;

  public ParameterDescriptor(TypeDescriptor type, String name, FunctionDescriptor func, int index) {
    super(type, name);
    assert func != null;
    this.function = func;
    this.index = index;
  }

  public FunctionDescriptor getFunction() {
    return function;
  }

  public int getIndex() {
    return index;
  }
}
