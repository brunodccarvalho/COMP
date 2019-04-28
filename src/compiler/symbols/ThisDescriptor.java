package compiler.symbols;

import java.util.Objects;

public class ThisDescriptor extends VariableDescriptor {
  private final FunctionLocals table;

  /**
   * Create a new this descriptor.
   *
   * @param table The table of function locals this variable will be added to
   */
  public ThisDescriptor(FunctionLocals locals) {
    super(locals.getFunction().getParentClass(), "this");
    this.table = locals;
  }

  /**
   * @return The this's class.
   */
  @Override
  public JMMClassDescriptor getType() {
    return (JMMClassDescriptor) super.getType();
  }

  /**
   * @return The this's table.
   */
  public FunctionLocals getTable() {
    return table;
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
    if (!(obj instanceof ThisDescriptor)) return false;
    ThisDescriptor other = (ThisDescriptor) obj;
    return Objects.equals(table, other.table);
  }
}
