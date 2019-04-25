package compiler.symbols;

import java.util.Objects;

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

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Objects.hash(parent);
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!super.equals(obj)) return false;
    if (!(obj instanceof MemberDescriptor)) return false;
    MemberDescriptor other = (MemberDescriptor) obj;
    return Objects.equals(parent, other.parent);
  }
}
