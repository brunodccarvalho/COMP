package compiler.dag;

import compiler.symbols.TypeDescriptor;
import compiler.symbols.VoidDescriptor;

public class DAGVoidReturn extends DAGReturn {
  @Override
  public VoidDescriptor getType() {
    return TypeDescriptor.voidDescriptor;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "return;\n";
  }
}
