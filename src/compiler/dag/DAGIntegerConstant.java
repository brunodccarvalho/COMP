package compiler.dag;

import java.util.Objects;

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

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return Integer.toString(constant);
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
    if (!(obj instanceof DAGIntegerConstant)) return false;
    DAGIntegerConstant other = (DAGIntegerConstant) obj;
    return constant == other.constant;
  }
}
