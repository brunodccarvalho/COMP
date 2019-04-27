package compiler.dag;

import static jjt.jmmTreeConstants.*;

import compiler.symbols.FunctionLocals;
import jjt.SimpleNode;

public class NodeFactory extends BaseDAGFactory {
  private FunctionLocals locals;

  public NodeFactory(FunctionLocals locals) {
    super(locals);
  }

  @Override
  public DAGNode build(SimpleNode node) {
    assert node != null;

    BaseDAGFactory factory;

    switch (node.getId()) {
    case JJTASSIGNMENT:
      factory = new AssignmentFactory(locals);
      break;
    case JJTIFELSESTATEMENT:
    case JJTBLOCKSTATEMENT:
    case JJTWHILESTATEMENT:
      assert false;
      return null;
    default:
      factory = new ExpressionFactory(locals);
      break;
    }

    DAGNode result = factory.build(node);
    status(factory.status());
    return result;
  }
}
