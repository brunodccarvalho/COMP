package compiler.dag;

public abstract class DAGNew extends DAGExpression {
  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return obj instanceof DAGNew && this == obj;
  }
}
