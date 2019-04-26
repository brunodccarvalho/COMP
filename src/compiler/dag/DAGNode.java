package compiler.dag;

/**
 * Base class of all DAG nodes
 *
 * DAG = Directed Acyclid Graph internal representation
 */
public abstract class DAGNode {
  protected boolean foundSemanticError = false;

  protected void setSemanticError() {
    foundSemanticError = true;
  }

  /**
   * Verify if there was any semantic error constructing this DAG node.
   */
  public boolean hasSemanticError() {
    return foundSemanticError;
  }
}
