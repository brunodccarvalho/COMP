package compiler.symbols;

import compiler.FunctionSignature;

/**
 * The base class of any Java class descriptor. This class has a non fully
 * qualified class name and can resolve both instance and static names from
 * among its (static and non-static) data members.
 */
public abstract class ClassDescriptor extends TypeDescriptor {
  /**
   * Creates a new class descriptor with the given name. The name must not exist
   * in the TypeDescriptor's type name table.
   */
  public ClassDescriptor(String name) {
    super(name);
  }

  /**
   * Returns this class's name.
   */
  public String getClassName() {
    return getName();
  }

  public boolean isPrimitive() {
    return false;
  }

  public boolean isClass() {
    return true;
  }

  public boolean isArray() {
    return false;
  }

  /**
   * Returns true if this class has a data member identified by name.
   */
  public abstract boolean hasMethod(String name);

  /**
   * Returns true if this class has at least one member method identified by name
   * and matching the corresponding signature.
   */
  public abstract boolean hasMethod(String name, FunctionSignature signature);

  /**
   * Resolve the given name non-statically.
   */
  public abstract Descriptor resolve(String name);

  /**
   * Resolve the given name statically.
   */
  public abstract Descriptor resolveStatic(String name);
}
