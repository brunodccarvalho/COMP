package compiler.dag;

import compiler.modules.CompilationStatus;
import compiler.symbols.FunctionLocals;
import jjt.SimpleNode;

/**
 * Base class of DAG factories. Exposes a build() function which takes a SimpleNode, holds the
 * function's FunctionLocals instance, and extends CompilerModule for error propagation.
 *
 * There are three factories: ExpressionFactory, AssignmentFactory, and ControlFlowFactory.
 */
public abstract class BaseDAGFactory extends CompilationStatus {
  final FunctionLocals locals;
  final CompilationStatus tracker;

  /**
   * Construct a new factory based on this symbol table.
   *
   * @param locals the function's symbol table.
   */
  BaseDAGFactory(FunctionLocals locals, CompilationStatus tracker) {
    assert locals != null && tracker != null;
    this.locals = locals;
    this.tracker = tracker;
  }

  /**
   * Parse and analyze the AST node given and return the resulting DAGNode.
   *
   * @param node A new JJT node to be parsed and semantically analyzed.
   * @return The resulting DAGNode.
   */
  public abstract DAGNode build(SimpleNode node);

  @Override
  public int update(int status) {
    tracker.update(super.update(status));
    return super.status();
  }

  @Override
  public int status() {
    return super.status();
  }
}
