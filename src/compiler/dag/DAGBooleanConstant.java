package compiler.dag;

import java.util.Objects;

import compiler.symbols.PrimitiveDescriptor;

public class DAGBooleanConstant extends DAGExpression {
  final boolean constant;

  DAGBooleanConstant(boolean constant) {
    this.constant = constant;
  }

  /**
   * @return The value of the boolean constant, true or false.
   */
  public boolean getValue() {
    return constant;
  }

  @Override
  public PrimitiveDescriptor getType() {
    return PrimitiveDescriptor.booleanDescriptor;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return constant ? "true" : "false";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(constant);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof DAGBooleanConstant)) return false;
    DAGBooleanConstant other = (DAGBooleanConstant) obj;
    return constant == other.constant;
  }
}
