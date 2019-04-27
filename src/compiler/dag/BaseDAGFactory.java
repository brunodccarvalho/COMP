package compiler.dag;

import compiler.modules.CompilerModule;
import jjt.SimpleNode;

public abstract class BaseDAGFactory extends CompilerModule {
  /**
   * Parse and analyze the AST node given and return the resulting DAGNode.
   *
   * @param node A new JJT node to be parsed and semantically analyzed.
   * @return The resulting DAGNode.
   */
  public abstract DAGNode build(SimpleNode node);
}
