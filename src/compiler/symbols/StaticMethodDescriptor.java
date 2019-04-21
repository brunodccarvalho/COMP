package compiler.symbols;

/**
 * Represents a static class method.
 */
public class StaticMethodDescriptor extends FunctionDescriptor {
  private ClassDescriptor parent;

  public StaticMethodDescriptor(ClassDescriptor parent, String name, FunctionSignature signature, String[] params) {
    super(name, signature, params);
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
