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
   *
   * @param name The class's name
   */
  public ClassDescriptor(String name) {
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

  /**
   * @param name A method's name
   * @return true if this class has at least one member method identified by name.
   */
  public abstract boolean hasMethod(String name);

  /**
   * @param name      A method's name
   * @param signature A method's signature
   * @return true if this class has at least one member method identified by name
   *         and matching the corresponding signature.
   */
  public abstract boolean hasMethod(String name, FunctionSignature signature);

  /**
   * @param name       A method's name
   * @param signature  A method's deduced signature
   * @param returnType A method's deduced return type
   * @return true if this class has at least one member method identified by name
   *         and matching the corresponding signature and returning the specified
   *         type.
   */
  public abstract boolean hasReturning(String name, FunctionSignature signature, TypeDescriptor returnType);

  /**
   * @param name The variable name to be resolved non-statically
   * @return The variable descriptor for the given name, or null if not found.
   */
  public abstract VariableDescriptor resolve(String name);

  /**
   * @param name The variable name to be resolved statically
   * @return The variable descriptor for the given name, or null if not found.
   */
  public abstract VariableDescriptor resolveStatic(String name);
}
