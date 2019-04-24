package compiler.symbols;

import compiler.FunctionSignature;

/**
 * A descriptor for a class's instance method.
 */
public class MethodDescriptor extends FunctionDescriptor {
  private final ClassDescriptor parent;

  public MethodDescriptor(ClassDescriptor parent, String name, TypeDescriptor ret, FunctionSignature signature,
      String[] params) {
    super(name, ret, signature, params);
    assert parent != null;
    this.parent = parent;
  }

  public ClassDescriptor getThis() {
    return parent;
  }

  @Override
  public Descriptor resolve(String name) {
    Descriptor var = super.resolve(name);
    if (var != null)
      return var;
    else
      return parent.resolve(name);
  }
}
