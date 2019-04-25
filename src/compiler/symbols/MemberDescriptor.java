package compiler.symbols;

/**
 * A descriptor for a class's data member.
 */
public class MemberDescriptor extends VariableDescriptor {
  private final JMMClassDescriptor parent;

  /**
   * Create a new member variable descriptor.
   *
   * @param type   The member variable's type
   * @param name   The member variable's name
   * @param parent The instance class this member variable will be added to.
   */
  public MemberDescriptor(TypeDescriptor type, String name, JMMClassDescriptor parent) {
    super(type, name);
    assert parent != null && !parent.hasMember(name);
    this.parent = parent;
    parent.addMember(this);
  }

  /**
   * @return The member variable's instance class.
   */
  public JMMClassDescriptor getParentClass() {
    return parent;
  }
}
