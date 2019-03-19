/* Generated By:JJTree: Do not edit this line. Node.java Version 6.0 */
/* JavaCCOptions:MULTI=false,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true
 */

public interface Node {
  /**
   * This method is called after the node has been made the current
   * node.  It indicates that child nodes can now be added to it.
   */
  public void jjtOpen();

  /**
   * This method is called after all the child nodes have been added.
   */
  public void jjtClose();

  /**
   * This pair of methods are used to inform the node of its parent.
   */
  public void jjtSetParent(Node n);
  public Node jjtGetParent();

  /**
   * This method tells the node to add its argument to the node's
   * list of children.
   */
  public void jjtAddChild(Node n, int i);

  /**
   * This method returns a child node.  The children are numbered
   * from zero, left to right.
   */
  public Node jjtGetChild(int i);

  /**
   * Return the number of children the node has.
   */
  public int jjtGetNumChildren();

  public int getId();

  /**
   * Returns beginLine of the first token in this node's tree
   */
  public int treeBeginLine();

  /**
   * Returns beginColumn of the first token in this node's tree
   */
  public int treeBeginColumn();

  /**
   * Returns endLine of the first token in this node's tree
   */
  public int treeEndLine();

  /**
   * Returns endColumn of the first token in this node's tree
   */
  public int treeEndColumn();
}
/* JavaCC - OriginalChecksum=67bfa86ed80de68cf7b6c76c2a76c325 (do not edit this line) */
