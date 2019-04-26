package compiler.dag;

import compiler.symbols.TypeDescriptor;

/**
 * Base class of an expression.
 *
 * A DAG expression has a type.
 */
public abstract class DAGExpression extends DAGNode {
  /**
   * @return The deduced type of this expression.
   */
  public abstract TypeDescriptor getType();
}
