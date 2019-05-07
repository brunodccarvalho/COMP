package compiler.dag;

import compiler.symbols.TypeDescriptor;

public abstract class DAGReturn extends DAGNode {
  /**
   * @return The return type proclaimed by this return.
   */
  public abstract TypeDescriptor getType();
}
