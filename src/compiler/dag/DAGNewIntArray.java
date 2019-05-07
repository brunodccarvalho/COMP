package compiler.dag;

import java.util.Objects;

import compiler.symbols.PrimitiveDescriptor;

public class DAGNewIntArray extends DAGNew {
  final DAGExpression indexExpression;

  DAGNewIntArray(DAGExpression indexExpression) {
    assert indexExpression != null;
    this.indexExpression = indexExpression;
  }

  /**
   * @return The expression for the index of the new array object.
   */
  public DAGExpression getIndexExpression() {
    return indexExpression;
  }

  @Override
  public PrimitiveDescriptor getType() {
    return PrimitiveDescriptor.intArrayDescriptor;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "new int[" + indexExpression + "]";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(indexExpression);
  }
}
