package compiler.dag;

import compiler.symbols.PrimitiveDescriptor;

public class DAGBooleanConstant extends DAGExpression {
  protected boolean constant;

  DAGBooleanConstant(boolean constant) {
    this.constant = constant;
  }

  public boolean getValue() {
    return constant;
  }

  @Override
  public PrimitiveDescriptor getType() {
    return PrimitiveDescriptor.booleanDescriptor;
  }
}
