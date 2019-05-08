package compiler.dag;

import java.util.Objects;

public class DAGWhile extends DAGBranch {
  protected final DAGNode body;

  DAGWhile(DAGCondition condition, DAGNode whileBody) {
    super(condition);
    assert whileBody != null;
    this.body = whileBody;
  }

  /**
   * @return the DAGNode of the body of this while statement.
   */
  public DAGNode getBody() {
    return body;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "while (" + super.toString() + ") " + body.toString();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Objects.hash(body);
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!super.equals(obj)) return false;
    if (!(obj instanceof DAGWhile)) return false;
    DAGWhile other = (DAGWhile) obj;
    return Objects.equals(body, other.body);
  }
}
