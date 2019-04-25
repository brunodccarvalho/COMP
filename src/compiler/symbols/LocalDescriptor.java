package compiler.symbols;

/**
 * Descriptor for a local variable instance.
 */
public class LocalDescriptor extends VariableDescriptor {
  private final FunctionLocals table;

  /**
   * Create a new local variable descriptor.
   *
   * @param type  The local variable's type
   * @param name  The local variable's name
   * @param table The table of function locals this variable will be added to.
   */
  public LocalDescriptor(TypeDescriptor type, String name, FunctionLocals table) {
    super(type, name);
    assert table != null && !table.hasVariable(name);
    this.table = table;
    table.addVariable(this);
  }

  /**
   * @return The local's table.
   */
  public FunctionLocals getTable() {
    return table;
  }

  /**
   * @return The local's containing function.
   */
  public BaseFunctionDescriptor getFunction() {
    return table.getFunction();
  }
}
