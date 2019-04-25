package compiler.symbols;

import java.util.Objects;

/**
 * Descriptor for a variable instance, may it be a data member, a parameter or a
 * local variable. It simply groups a type descriptor with a name. The name is
 * used to address it in various hash maps (symbol tables).
 */
public abstract class VariableDescriptor extends Descriptor {
  protected final TypeDescriptor type;
  protected final String name;

  /**
   * Creates a new variable descriptor.
   *
   * @param type The variable's type
   * @param name The variable's name
   */
  protected VariableDescriptor(TypeDescriptor type, String name) {
    assert type != null && name != null;
    this.type = type;
    this.name = name;
  }

  /**
   * @return The variable's type descriptor
   */
  public TypeDescriptor getType() {
    return type;
  }

  /**
   * @return The variable's name
   */
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return type + " " + name;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, type);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof VariableDescriptor)) return false;
    VariableDescriptor other = (VariableDescriptor) obj;
    return Objects.equals(name, other.name) && Objects.equals(type, other.type);
  }
}
