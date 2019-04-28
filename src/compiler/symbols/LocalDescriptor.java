package compiler.symbols;

import java.util.Objects;

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
  public JMMFunction getFunction() {
    return table.getFunction();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Objects.hash(table);
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!super.equals(obj)) return false;
    if (!(obj instanceof LocalDescriptor)) return false;
    LocalDescriptor other = (LocalDescriptor) obj;
    return Objects.equals(table, other.table);
  }
}
