package compiler.dag;

import compiler.symbols.PrimitiveDescriptor;

public class DAGLength extends DAGExpression {
  protected DAGExpression expression;

  DAGLength(DAGExpression expression) {
    this.expression = expression;
  }

  public DAGExpression getExpression() {
    return expression;
  }

  @Override
  public PrimitiveDescriptor getType() {
    return PrimitiveDescriptor.intDescriptor;
  }
}
