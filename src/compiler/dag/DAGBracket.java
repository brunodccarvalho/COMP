package compiler.dag;

import java.util.Objects;

import compiler.symbols.PrimitiveDescriptor;

public class DAGBracket extends DAGExpression {
  final DAGExpression arrayExpression;
  final DAGExpression indexExpression;

  DAGBracket(DAGExpression array, DAGExpression index) {
    assert array != null && index != null;
    this.arrayExpression = array;
    this.indexExpression = index;
  }

  /**
   * (ArrayExpression)[IndexExpression]
   *
   * @return the ArrayExpression
   */
  public DAGExpression getArrayExpression() {
    return arrayExpression;
  }

  /**
   * (ArrayExpression)[IndexExpression]
   *
   * @return the IndexExpression
   */
  public DAGExpression getIndexExpression() {
    return indexExpression;
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
    return arrayExpression + "[" + indexExpression + "]";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(arrayExpression, indexExpression);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof DAGBracket)) return false;
    DAGBracket other = (DAGBracket) obj;
    return Objects.equals(arrayExpression, other.arrayExpression)
        && Objects.equals(indexExpression, other.indexExpression);
  }
}
