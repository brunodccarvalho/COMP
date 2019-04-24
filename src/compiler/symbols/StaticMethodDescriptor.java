package compiler.symbols;

import compiler.FunctionSignature;

/**
 * A descriptor for a class's static method.
 *
 * Note: unused.
 */
public class StaticMethodDescriptor extends FunctionDescriptor {
  private final ClassDescriptor parent;

  public StaticMethodDescriptor(ClassDescriptor parent, String name, TypeDescriptor ret, FunctionSignature signature,
      String[] params) {
    super(name, ret, signature, params);
    assert parent != null;
    this.parent = parent;
  }

  public ClassDescriptor getParentClass() {
    return parent;
  }

  @Override
  public Descriptor resolve(String name) {
    Descriptor var = super.resolve(name);
    if (var != null)
      return var;
    else
      return parent.resolveStatic(name);
  }
}
