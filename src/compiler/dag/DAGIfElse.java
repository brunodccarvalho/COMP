package compiler.dag;

import java.util.Objects;

public class DAGIfElse extends DAGBranch {
  final DAGNode thenBody;
  final DAGNode elseBody;

  DAGIfElse(DAGExpression condition, DAGNode thenBody, DAGNode elseBody) {
    super(condition);
    assert thenBody != null && elseBody != null;
    this.thenBody = thenBody;
    this.elseBody = elseBody;
  }

  /**
   * @return The DAGNode of the true branch.
   */
  public DAGNode getThenNode() {
    return thenBody;
  }

  /**
   * @return The DAGNode of the false branch.
   */
  public DAGNode getElseNode() {
    return elseBody;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "if (" + condition + ") " + thenBody + "\nelse " + elseBody;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Objects.hash(elseBody, thenBody);
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!super.equals(obj)) return false;
    if (!(obj instanceof DAGIfElse)) return false;
    DAGIfElse other = (DAGIfElse) obj;
    return Objects.equals(elseBody, other.elseBody) && Objects.equals(thenBody, other.thenBody);
  }
}
