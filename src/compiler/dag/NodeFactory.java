package compiler.dag;

import static jjt.jmmTreeConstants.*;

import compiler.modules.CompilerModule;
import compiler.symbols.FunctionLocals;
import jjt.SimpleNode;

public class NodeFactory extends BaseDAGFactory {
  private FunctionLocals locals;

  public NodeFactory(FunctionLocals locals) {
    this.locals = locals;
  }

  @Override
  public DAGNode build(SimpleNode node) {
    assert node != null;

    BaseDAGFactory factory;

    switch (node.getId()) {
    case JJTASSIGNMENT:
      factory = new AssignmentFactory(locals);
      break;
    default:
      factory = new ExpressionFactory(locals);
      break;
    }

    DAGNode result = factory.build(node);
    status(factory.status());
    return result;
  }
}
