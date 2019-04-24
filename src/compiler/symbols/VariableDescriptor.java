package compiler.symbols;

/**
 * Descriptor for a variable instance, may it be a data member, a parameter or a
 * local variable. It simply groups a type descriptor with a name. The name is
 * used to address it in various hash maps (symbol tables).
 */
public abstract class VariableDescriptor extends Descriptor {
  protected final TypeDescriptor type;
  protected final String name;

  public VariableDescriptor(TypeDescriptor type, String name) {
    assert type != null && name != null;
    this.type = type;
    this.name = name;
  }

  public TypeDescriptor getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return type + " " + name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
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
    VariableDescriptor other = (VariableDescriptor) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    return true;
  }
}
