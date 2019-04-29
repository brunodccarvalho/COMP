package compiler.dag;

import static jjt.jmmTreeConstants.*;

import compiler.symbols.PrimitiveDescriptor;
import jjt.SimpleNode;

/**
 * Binary operator auxiliary for DAGBinaryOp.
 */
public enum BinaryOperator {
  SUM("+"),
  SUB("-"),
  MUL("*"),
  DIV("/"),
  LT("<"),
  AND("&&");

  private final String str;

  BinaryOperator(String s) {
    this.str = s;
  }

  /**
   * Get the resulting type of the operation: typeof(Expression Op Expression)
   *
   * @return the descriptor of the resulting type.
   */
  public PrimitiveDescriptor getExpressionType() {
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

  /**
   * Get the type of each operand: typeof(Expression) Op Expression
   *
   * @return the descriptor of the operand type.
   */
  public PrimitiveDescriptor getOperandType() {
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

  /**
   * Get binary operator for this SimpleNode.
   */
  public static BinaryOperator from(SimpleNode node) {
    switch (node.getId()) {
    case JJTAND:
      return AND;
    case JJTLT:
      return LT;
    case JJTSUM:
      return SUM;
    case JJTSUB:
      return SUB;
    case JJTMUL:
      return MUL;
    case JJTDIV:
      return DIV;
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
