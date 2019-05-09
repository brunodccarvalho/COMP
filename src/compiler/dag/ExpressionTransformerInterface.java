package compiler.dag;

interface ExpressionTransformerInterface {
  /**
   * Optimize the given DAGExpression node. A simple implementation of this method simply
   * returns the given node immediately, and no optimization is performed in expressions.
   * In other cases, constant evaluation may take place.
   *
   * @param expression The DAGExpression to be optimized.
   * @return The DAGExpression node; possibly modified; possibly a new one altogether.
   */
  abstract DAGExpression optimize(DAGExpression expression);
}
