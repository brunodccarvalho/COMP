package compiler.symbols;

/**
 * Descriptor for a local variable instance.
 */
public class LocalVariableDescriptor extends VariableDescriptor {
  private final FunctionDescriptor function;

  public LocalVariableDescriptor(TypeDescriptor type, String name, FunctionDescriptor function) {
    super(type, name);
    assert function != null;
    this.function = function;
  }

  public FunctionDescriptor getFunction() {
    return function;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((function == null) ? 0 : function.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    LocalVariableDescriptor other = (LocalVariableDescriptor) obj;
    if (function == null) {
      if (other.function != null)
        return false;
    } else if (!function.equals(other.function))
      return false;
    return true;
  }
}
