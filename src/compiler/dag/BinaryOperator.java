package compiler.dag;

import compiler.symbols.PrimitiveDescriptor;
import compiler.symbols.TypeDescriptor;

public enum BinaryOperator {
  SUM("+"),
  SUB("-"),
  MUL("*"),
  DIV("/"),
  LT("<"),
  AND("&&");

  private String str;

  BinaryOperator(String s) {
    this.str = s;
  }

  public TypeDescriptor getExpressionType() {
    switch (this) {
    case SUM:
    case SUB:
    case MUL:
    case DIV:
      return PrimitiveDescriptor.intDescriptor;
    case LT:
    case AND:
      return PrimitiveDescriptor.booleanDescriptor;
    }

    // We should never arrive here
    assert false;
    return null;
  }

  public TypeDescriptor getOperandType() {
    switch (this) {
    case SUM:
    case SUB:
    case MUL:
    case DIV:
    case LT:
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
