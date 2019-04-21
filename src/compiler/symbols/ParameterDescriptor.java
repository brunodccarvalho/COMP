package compiler.symbols;

public class ParameterDescriptor extends VariableDescriptor {
  private FunctionDescriptor function;
  private int index;

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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((function == null) ? 0 : function.hashCode());
    result = prime * result + index;
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
    ParameterDescriptor other = (ParameterDescriptor) obj;
    if (function == null) {
      if (other.function != null)
        return false;
    } else if (!function.equals(other.function))
      return false;
    if (index != other.index)
      return false;
    return true;
  }
}
