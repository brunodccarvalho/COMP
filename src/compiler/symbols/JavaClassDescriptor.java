package compiler.symbols;

import compiler.FunctionSignature;

public class JavaClassDescriptor extends ClassDescriptor {
  /**
   * Creates a new Java Class descriptor. The members and methods of this class
   * are unknown.
   */
  public JavaClassDescriptor(String name) {
    super(name);
  }

  /**
   * Returns true if this class has a data member identified by name.
   */
  public boolean hasMethod(String name) {
    return true;
  }

  /**
   * Returns true if this class has at least one member method identified by name
   * and matching the corresponding signature.
   */
  public boolean hasMethod(String name, FunctionSignature signature) {
    return true;
  }

  /**
   * Resolve the given name non-statically.
   */
  public Descriptor resolve(String name) {
    return null;
  }

  /**
   * Resolve the given name statically.
   */
  public Descriptor resolveStatic(String name) {
    return null;
  }
}
