package compiler.symbols;

public abstract class BaseFunctionDescriptor extends Descriptor {
  protected String functionName;

  protected BaseFunctionDescriptor(String name) {
    assert name != null;
    this.functionName = name;
  }

  public String getName() {
    return functionName;
  }

  public abstract boolean hasParameter(String name);

  public abstract boolean hasParameters();

  public abstract int getNumParameters();

  public abstract VariableDescriptor resolve(String name);

  @Override
  public String toString() {
    return functionName + "(...)";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((functionName == null) ? 0 : functionName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    BaseFunctionDescriptor other = (BaseFunctionDescriptor) obj;
    if (functionName == null) {
      if (other.functionName != null)
        return false;
    } else if (!functionName.equals(other.functionName))
      return false;
    return true;
  }
}
