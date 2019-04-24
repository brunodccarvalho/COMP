package compiler.symbols;

/**
 * Descriptor for a local variable instance.
 */
public class LocalDescriptor extends VariableDescriptor {
  private final FunctionDescriptor function;

  public LocalDescriptor(TypeDescriptor type, String name, FunctionDescriptor function) {
    super(type, name);
    assert function != null;
    this.function = function;
  }

  public FunctionDescriptor getFunction() {
    return function;
  }
}
