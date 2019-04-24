package compiler.symbols;

/**
 * A descriptor for a class's data member.
 */
public class MemberDescriptor extends VariableDescriptor {
  private final ClassDescriptor instanceClass;

  public MemberDescriptor(TypeDescriptor type, String name, ClassDescriptor instanceClass) {
    super(type, name);
    assert instanceClass != null;
    this.instanceClass = instanceClass;
  }

  public ClassDescriptor getParentClass() {
    return instanceClass;
  }
}
