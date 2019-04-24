/* Generated By:JJTree: Do not edit this line. SimpleNode.java Version 6.0 */
/* JavaCCOptions:MULTI=false,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true
 */
package jjt;

public class SimpleNode implements Node {
  protected Node parent;
  protected Node[] children;
  protected int id;
  protected String val;
  protected jmm parser;
  protected Token firstToken;
  protected Token lastToken;

  public SimpleNode(int i) {
    id = i;
  }

  public SimpleNode(jmm p, int i) {
    this(i);
    parser = p;
  }

  public void jjtOpen() {
  }

  public void jjtClose() {
  }

  public void jjtSetParent(Node n) {
    parent = n;
  }

  public Node jjtGetParent() {
    return parent;
  }

  public void jjtAddChild(Node n, int i) {
    if (children == null) {
      children = new Node[i + 1];
    } else if (i >= children.length) {
      Node c[] = new Node[i + 1];
      System.arraycopy(children, 0, c, 0, children.length);
      children = c;
    }
    children[i] = n;
  }

  public SimpleNode jjtGetChild(int i) {
    return (SimpleNode) children[i];
  }

  public int jjtGetNumChildren() {
    return (children == null) ? 0 : children.length;
  }

  public void jjtSetVal(String val) {
    this.val = val;
  }

  public String jjtGetVal() {
    return val;
  }

  public Token jjtGetFirstToken() {
    return firstToken;
  }

  public void jjtSetFirstToken(Token token) {
    this.firstToken = token;
  }

  public Token jjtGetLastToken() {
    return lastToken;
  }

  public void jjtSetLastToken(Token token) {
    this.lastToken = token;
  }

  public int getId() {
    return id;
  }

  private static boolean DUMP_TRACK = true;

  public String toString() {
    String nodename = jmmTreeConstants.jjtNodeName[id];
    if (DUMP_TRACK) {
      if (val != null)
        return nodename + " " + val + " " + getRange();
      else
        return nodename + " " + getRange();
    } else {
      if (val != null)
        return nodename + " " + val;
      else
        return nodename;
    }
  }

  public String toString(String prefix) {
    return prefix + toString();
  }

  public void dump(String prefix) {
    System.out.println(toString(prefix));
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        SimpleNode n = (SimpleNode) children[i];
        if (n != null) {
          n.dump(prefix + " ");
        }
      }
    }
  }

  protected int rangeCompare(int[] range1, int[] range2) {
    if (range1[0] != range2[0]) {
      return range1[0] - range2[0];
    } else {
      return range1[1] - range2[1];
    }
  }

  int[] cacheBegin, cacheEnd;

  protected int[] treeBegin() {
    if (cacheBegin != null)
      return cacheBegin;
    int[] mine = { firstToken.beginLine, firstToken.beginColumn };

    if (children != null && children.length > 0) {
      SimpleNode childnode = (SimpleNode) children[0];
      int[] childs = childnode.treeBegin();
      cacheBegin = rangeCompare(mine, childs) < 0 ? mine : childs; // min
    } else {
      cacheBegin = mine;
    }

    // Sanity check, fix for MethodName length
    int[] end = { lastToken.beginLine, lastToken.beginColumn };
    if (rangeCompare(cacheBegin, end) < 0) {
      cacheBegin = end;

      if (children != null && children.length > 0) {
        SimpleNode childnode = (SimpleNode) children[0];
        int[] childs = childnode.treeBegin();
        cacheBegin = rangeCompare(end, childs) < 0 ? end : childs;
      }
    }

    return cacheBegin;
  }

  protected int[] treeEnd() {
    if (cacheEnd != null)
      return cacheEnd;
    int[] mine = { lastToken.endLine, lastToken.endColumn + 1 };

    if (children != null && children.length > 0) {
      SimpleNode childnode = (SimpleNode) children[children.length - 1];
      int[] childs = childnode.treeEnd();
      cacheEnd = rangeCompare(mine, childs) < 0 ? childs : mine; // max
    } else {
      cacheEnd = mine;
    }

    return cacheEnd;
  }

  protected String getRange() {
    int[] begin = treeBegin(), end = treeEnd();
    return begin[0] + ":" + begin[1] + " " + end[0] + ":" + end[1];
  }

  public boolean is(int supposed) {
    return this.id == supposed;
  }
}
/*
 * JavaCC - OriginalChecksum=a86277efa9546bcd3b308dafc0a9690c (do not edit this
 * line)
 */
