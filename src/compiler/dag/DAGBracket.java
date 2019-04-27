package compiler.dag;

import compiler.symbols.PrimitiveDescriptor;

public class DAGBracket extends DAGExpression {
  protected DAGExpression arrayExpression;
  protected DAGExpression indexExpression;

  DAGBracket(DAGExpression array, DAGExpression index) {
    this.arrayExpression = array;
    this.indexExpression = index;
  }

  public DAGExpression getArrayExpression() {
    return arrayExpression;
  }

  public DAGExpression getIndexExpression() {
    return indexExpression;
  }

  @Override
  public PrimitiveDescriptor getType() {
    return PrimitiveDescriptor.intDescriptor;
  }
}
