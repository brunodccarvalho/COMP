package compiler.dag;

import static jjt.jmmTreeConstants.*;

import compiler.symbols.FunctionLocals;
import jjt.SimpleNode;

public class NodeFactory extends BaseDAGFactory {
  public NodeFactory(FunctionLocals locals) {
    super(locals);
  }

  @Override
  public DAGNode build(SimpleNode node) {
    assert node != null;

    if (!node.is(JJTPLAINSTATEMENT)) {
      System.err.println("Not implemented yet; cannot build node " + node);
      status(FATAL);
      return null;
    }

    SimpleNode childNode = node.jjtGetChild(0);

    BaseDAGFactory factory;

    switch (childNode.getId()) {
    case JJTASSIGNMENT:
      factory = new AssignmentFactory(locals);
      break;
    case JJTIFELSESTATEMENT:
    case JJTBLOCKSTATEMENT:
    case JJTWHILESTATEMENT:
      // Control flow not implemented yet.
      assert false;
      return null;
    default:
      factory = new ExpressionFactory(locals);
      break;
    }

    DAGNode result = factory.build(childNode);
    status(factory.status());
    return result;
  }
}
