package compiler.symbols;

/**
 * A descriptor for a class's data member.
 */
public class MemberVariableDescriptor extends VariableDescriptor {
  private final ClassDescriptor instanceClass;

  public MemberVariableDescriptor(TypeDescriptor type, String name, ClassDescriptor instanceClass) {
    super(type, name);
    assert instanceClass != null;
    this.instanceClass = instanceClass;
  }

  public ClassDescriptor getParentClass() {
    return instanceClass;
  }

  @Override
  public String toString() {
    return type.toString() + ' ' + instanceClass.getClassName() + '.' + name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((instanceClass == null) ? 0 : instanceClass.hashCode());
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
    MemberVariableDescriptor other = (MemberVariableDescriptor) obj;
    if (instanceClass == null) {
      if (other.instanceClass != null)
        return false;
    } else if (!instanceClass.equals(other.instanceClass))
      return false;
    return true;
  }
}
