package compiler.dag;

import compiler.symbols.PrimitiveDescriptor;
import compiler.symbols.TypeDescriptor;

public class DAGNewIntArray extends DAGNew {
  protected DAGExpression indexExpression;

  DAGNewIntArray(DAGExpression indexExpression) {
    this.indexExpression = indexExpression;
  }

  public DAGExpression getIndex() {
    return indexExpression;
  }

  @Override
  public TypeDescriptor getType() {
    return PrimitiveDescriptor.intArrayDescriptor;
  }
}
