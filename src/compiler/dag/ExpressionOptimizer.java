package compiler.dag;

import java.util.HashMap;

/**
 * Place all expression optimizations here...
 */
class ExpressionOptimizer extends ExpressionTransformer {
  /**
   * Set of DAG expressions already constructed, for common subexpression elimination.
   */
  private final HashMap<DAGExpression, DAGExpression> cache = new HashMap<>();

  ExpressionOptimizer(ExpressionFactory factory) {
    super(factory);
  }

  private DAGExpression reuse(DAGExpression expression) {
    if (cache.containsKey(expression)) {
      return cache.get(expression);
    } else {
      cache.put(expression, expression);
      return expression;
    }
  }

  @Override
  public DAGExpression optimize(DAGExpression expression) {
    expression = reuse(expression);

    // ...

    return expression;
  }
}
