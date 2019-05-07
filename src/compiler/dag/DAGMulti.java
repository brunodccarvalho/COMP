package compiler.dag;

import java.util.Arrays;

public class DAGMulti extends DAGNode {
  final DAGNode[] body;

  DAGMulti(DAGNode[] body) {
    assert body != null;
    this.body = body;
  }

  /**
   * @return The number of DAGNodes in this block.
   */
  public int getNumChildren() {
    return body.length;
  }

  /**
   * @return The DAGNodes in this block.
   */
  public DAGNode[] getNodes() {
    return body.clone();
  }

  /**
   * @return The DAGNodes in this block.
   */
  public DAGNode[] getBody() {
    return body.clone();
  }

  /**
   * @return The ith DAGNode in this block.
   */
  public DAGNode getNode(int i) {
    return body[i];
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("{\n");
    for (DAGNode node : body) builder.append("  ").append(node.toString());
    builder.append("}");
    return builder.toString();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(body);
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof DAGMulti)) return false;
    DAGMulti other = (DAGMulti) obj;
    return Arrays.equals(body, other.body);
  }
}
