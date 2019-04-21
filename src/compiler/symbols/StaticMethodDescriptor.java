package compiler.symbols;

/**
 * Represents a static class method.
 */
class StaticMethodDescriptor extends FunctionDescriptor {
  private ClassDescriptor parent;

  public StaticMethodDescriptor(ClassDescriptor parent, String name, TypeDescriptor ret, VariableDescriptor[] params) {
    super(name, ret, params);
    assert parent != null;
    this.parent = parent;
  }

  public ClassDescriptor getParentClass() {
    return parent;
  }

  public Descriptor resolve(String name) {
    assert name != null;
    VariableDescriptor var = getParameter(name);
    if (var != null)
      return var;
    else
      return parent.resolveStatic(name);
  }
}
