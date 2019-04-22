package compiler.symbols;

/**
 * A descriptor for a class's instance method.
 */
public class MethodDescriptor extends FunctionDescriptor {
  private final ClassDescriptor parent;

  public MethodDescriptor(ClassDescriptor parent, String name, FunctionSignature signature, String[] params) {
    super(name, signature, params);
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
