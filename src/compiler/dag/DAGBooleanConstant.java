package compiler.dag;

import compiler.symbols.PrimitiveDescriptor;
import compiler.symbols.TypeDescriptor;

public class DAGBooleanConstant extends DAGExpression {
  protected boolean constant;

  DAGBooleanConstant(boolean constant) {
    this.constant = constant;
  }

  public boolean getValue() {
    return constant;
  }

  @Override
  public TypeDescriptor getType() {
    return PrimitiveDescriptor.booleanDescriptor;
  }
}
