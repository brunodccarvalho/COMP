package compiler.symbols;

/**
 * Represents a class instance method.
 */
class MethodDescriptor extends FunctionDescriptor {
  private ClassDescriptor parent;

  public MethodDescriptor(ClassDescriptor parent, String name, TypeDescriptor ret, VariableDescriptor[] params) {
    super(name, ret, params);
    assert parent != null;
    this.parent = parent;
  }

  public ClassDescriptor getThis() {
    return parent;
  }

  public Descriptor resolve(String name) {
    assert name != null;
    VariableDescriptor var = getParameter(name);
    if (var != null)
      return var;
    else
      return parent.resolve(name);
  }
}
