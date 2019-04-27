package compiler.dag;

import compiler.symbols.PrimitiveDescriptor;

public class DAGNot extends DAGExpression {
  protected DAGExpression expression;

  DAGNot(DAGExpression expression) {
    this.expression = expression;
  }

  public DAGExpression getExpression() {
    return expression;
  }

  @Override
  public PrimitiveDescriptor getType() {
    return PrimitiveDescriptor.booleanDescriptor;
  }
}
