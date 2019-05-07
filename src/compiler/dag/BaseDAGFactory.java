package compiler.dag;

import compiler.modules.CompilerModule;
import compiler.symbols.FunctionLocals;
import jjt.SimpleNode;

/**
 * Base class of DAG factories. Exposes a build() function which takes a SimpleNode, holds the
 * function's FunctionLocals instance, and extends CompilerModule for error propagation.
 *
 * There are three factories: ExpressionFactory, AssignmentFactory, and ControlFlowFactory.
 */
public abstract class BaseDAGFactory extends CompilerModule {
  final FunctionLocals locals;

  /**
   * Construct a new factory based on this symbol table.
   *
   * @param locals the function's symbol table.
   */
  BaseDAGFactory(FunctionLocals locals) {
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
