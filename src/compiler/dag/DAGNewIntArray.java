package compiler.dag;

import compiler.symbols.PrimitiveDescriptor;

public class DAGNewIntArray extends DAGNew {
  protected DAGExpression indexExpression;

  DAGNewIntArray(DAGExpression indexExpression) {
    this.indexExpression = indexExpression;
  }

  public DAGExpression getIndexExpression() {
    return indexExpression;
  }

  @Override
  public PrimitiveDescriptor getType() {
    return PrimitiveDescriptor.intArrayDescriptor;
  }
}
