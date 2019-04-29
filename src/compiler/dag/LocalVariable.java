package compiler.dag;

import compiler.symbols.TypeDescriptor;
import compiler.symbols.VariableDescriptor;

public interface LocalVariable {
  /**
   * @return the variable descriptor.
   */
  public VariableDescriptor getVariable();

  /**
   * @return the variable index in the local variables table.
   */
  public int getIndex();

  /**
   * @return the type of the variable.
   */
  public TypeDescriptor getType();
}
