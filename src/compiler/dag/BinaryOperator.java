package compiler.dag;

import compiler.symbols.PrimitiveDescriptor;
import compiler.symbols.TypeDescriptor;

public enum BinaryOperator {
  PLUS("+"),
  MINUS("-"),
  TIMES("*"),
  DIVIDE("/"),
  LESS("<"),
  AND("&&");

  private String str;

  BinaryOperator(String s) {
    this.str = s;
  }

  public TypeDescriptor getExpressionType() {
    switch (this) {
    case PLUS:
    case MINUS:
    case TIMES:
    case DIVIDE:
      return PrimitiveDescriptor.intDescriptor;
    case LESS:
    case AND:
      return PrimitiveDescriptor.booleanDescriptor;
    }

    // We should never arrive here
    assert false;
    return null;
  }

  public TypeDescriptor getOperandType() {
    switch (this) {
    case PLUS:
    case MINUS:
    case TIMES:
    case DIVIDE:
    case LESS:
      return PrimitiveDescriptor.intDescriptor;
    case AND:
      return PrimitiveDescriptor.booleanDescriptor;
    }

    // We should never arrive here
    assert false;
    return null;
  }

  @Override
  public String toString() {
    return str;
  }
}
