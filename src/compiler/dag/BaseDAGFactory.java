package compiler.dag;

import compiler.modules.CompilerModule;
import compiler.symbols.FunctionLocals;
import jjt.SimpleNode;

public abstract class BaseDAGFactory extends CompilerModule {
  protected FunctionLocals locals;

  protected BaseDAGFactory(FunctionLocals locals) {
    assert locals != null;
    this.locals = locals;
  }

  /**
   * Parse and analyze the AST node given and return the resulting DAGNode.
   *
   * @param node A new JJT node to be parsed and semantically analyzed.
   * @return The resulting DAGNode.
   */
  public abstract DAGNode build(SimpleNode node);
}
