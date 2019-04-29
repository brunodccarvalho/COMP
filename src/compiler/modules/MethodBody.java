package compiler.modules;

import compiler.dag.DAGExpression;
import compiler.dag.DAGNode;
import compiler.dag.ExpressionFactory;
import compiler.dag.NodeFactory;
import compiler.symbols.FunctionLocals;
import static jjt.jmmTreeConstants.*;
import jjt.SimpleNode;

// This class needs a different name, like 'BuilderDAG' or something...
public class MethodBody extends CompilerModule {
  final DAGNode[] statements;
  DAGExpression returnExpression;

  public MethodBody(FunctionLocals functionLocals, SimpleNode methodNode) {
    assert functionLocals != null && methodNode.is(JJTMETHODDECLARATION)
        || methodNode.is(JJTMAINDECLARATION);

    SimpleNode methodBodyNode = getMethodBodyNode(methodNode);

    // construct DAGNodes
    //  -- number SimpleNodes = N (Number of method body children)
    //  -- M (number of var declarations)
    //  -- number of DAGNodes is N - M.
    // to construct each DAG: new NodeFactory(locals).build(node);
    int numberDecl = 0;
    int numberChildren = methodBodyNode.jjtGetNumChildren();
    for (int i = 0; i < numberChildren; i++) {
      if (methodBodyNode.jjtGetChild(i).is(JJTVARIABLEDECLARATION))
        numberDecl++;
      else
        break;
    }
    int numberDAGnodes = numberChildren - numberDecl;

    // Build each of the DAGNodes. Exit with the highest error from the factories.
    this.statements = new DAGNode[numberDAGnodes];

    for (int p = numberDecl; p < numberChildren; p++) {
      SimpleNode statementNode = methodBodyNode.jjtGetChild(p);
      assert !statementNode.is(JJTVARIABLEDECLARATION);

      NodeFactory factory = new NodeFactory(functionLocals);
      DAGNode dagNode = factory.build(statementNode);
      status(factory.status());

      int i = p - numberDecl;
      this.statements[i] = dagNode;

      if (status() >= FATAL) return;
    }

    // Done with body's statements, possibly with errors...
    // Now get the Return Expression - if this method is not main.

    if (methodNode.is(JJTMAINDECLARATION)) return;

    SimpleNode returnNode = methodNode.jjtGetChild(4);
    assert returnNode.is(JJTRETURNSTATEMENT);

    SimpleNode returnExpressionNode = returnNode.jjtGetChild(0);
    ExpressionFactory factory = new ExpressionFactory(functionLocals);
    this.returnExpression = factory.build(returnExpressionNode);
    status(factory.status());
  }

  public DAGNode[] getStatements() {
    return this.statements;
  }

  public DAGExpression getReturnExpression() {
    return this.returnExpression;
  }

  private static SimpleNode getMethodBodyNode(SimpleNode node) {
    assert (node.is(JJTMETHODDECLARATION) || node.is(JJTMAINDECLARATION));
    if (node.is(JJTMETHODDECLARATION)) return node.jjtGetChild(3);
    return node.jjtGetChild(1);  // main declaration
  }
}
