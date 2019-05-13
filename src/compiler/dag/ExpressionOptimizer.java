package compiler.dag;

import compiler.symbols.TypeDescriptor;

class ExpressionOptimizer extends ExpressionTransformer {
  ExpressionOptimizer(ExpressionFactory factory) {
    super(factory);
  }

  @Override
  public DAGExpression optimize(DAGExpression expression) {
    return expression;
  }
}
