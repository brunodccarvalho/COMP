package compiler.symbols;

/**
 * The base class of any Java class descriptor. This class has a non fully
 * qualified class name and can resolve both instance and static names from
 * among its (static and non-static) data members.
 */
public abstract class ClassDescriptor extends TypeDescriptor implements ResolverClass {
  /**
   * Creates a new class descriptor with the given name. The name must not exist
   * in the TypeDescriptor's type name table.
   *
   * @param name The class's name
   */
  protected ClassDescriptor(String name) {
    super(name);
  }

  /**
   * @return This class's name.
   */
  public String getClassName() {
    return getName();
  }

  @Override
  public boolean isPrimitive() {
    return false;
  }

  @Override
  public boolean isClass() {
    return true;
  }

  @Override
  public boolean isArray() {
    return false;
  }

  @Override
  public String getBytecodeString() {
    return "L" + getName() + ";";
  }
}
