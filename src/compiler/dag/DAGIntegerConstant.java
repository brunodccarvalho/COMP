package compiler.dag;

import compiler.symbols.PrimitiveDescriptor;

public class DAGIntegerConstant extends DAGExpression {
  protected int constant;

  DAGIntegerConstant(int constant) {
    this.constant = constant;
  }

  public int getValue() {
    return constant;
  }

  @Override
  public PrimitiveDescriptor getType() {
    return PrimitiveDescriptor.intDescriptor;
  }
}
