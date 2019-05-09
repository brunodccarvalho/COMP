package compiler.dag;

import compiler.symbols.TypeDescriptor;
import jjt.SimpleNode;

class ExpressionResolver implements ExpressionResolverInterface {
  protected final ExpressionFactory factory;

  ExpressionResolver(ExpressionFactory factory) {
    assert factory != null;
    this.factory = factory;
  }

  @Override
  public DAGExpression assertType(DAGExpression expression, TypeDescriptor type, SimpleNode node) {
    return null;
  }

  @Override
  public DAGExpression assertStatement(DAGExpression expression, SimpleNode node) {
    return null;
  }
}
