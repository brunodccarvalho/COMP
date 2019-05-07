package compiler.dag;

import static jjt.jmmTreeConstants.*;
import static compiler.symbols.TypeDescriptor.*;
import static compiler.symbols.PrimitiveDescriptor.*;

import compiler.symbols.FunctionLocals;
import compiler.symbols.TypeDescriptor;
import jjt.SimpleNode;

public class NodeFactory extends BaseDAGFactory {
  public NodeFactory(FunctionLocals locals) {
    super(locals);
  }

  /**
   * Construct a DAGMulti representing this method's body.
   *
   * @param SimpleNode methodNode The MethodDeclaration or MainDeclaration node.
   * @return A new DAGMulti holding the method's statements, in sequence.
   */
  public DAGMulti buildMethod(SimpleNode methodNode) {
    assert methodNode.is(JJTMETHODDECLARATION) || methodNode.is(JJTMAINDECLARATION);

    SimpleNode methodBodyNode = getMethodBodyNode(methodNode);
    SimpleNode returnNode = getReturnStatementNode(methodNode);

    // Construct DAGNodes
    // -- N = (number of method body children)
    // -- M = (number of var declarations)
    // -- D = number of DAGNodes is N - M + 1, counting return statement.
    //
    int N = methodBodyNode.jjtGetNumChildren(), M;
    for (M = 0; M < N; ++M) {
      SimpleNode child = methodBodyNode.jjtGetChild(M);
      if (!child.is(JJTVARIABLEDECLARATION)) break;
    }
    int D = N - M + 1;
    DAGNode[] nodes = new DAGNode[D];  // at least 1

    for (int i = M; i < N; ++i) {
      SimpleNode statementNode = methodBodyNode.jjtGetChild(i);
      assert !statementNode.is(JJTVARIABLEDECLARATION);

      nodes[i] = build(statementNode);
    }

    if (returnNode == null) {
      nodes[D - 1] = buildVoidReturn();
    } else {
      nodes[D - 1] = buildReturn(returnNode);
    }

    return new DAGMulti(nodes);
  }

  @Override
  public DAGNode build(SimpleNode node) {
    assert node != null;

    switch (node.getId()) {
    case JJTPLAINSTATEMENT:
      return buildPlain(node);
    case JJTWHILESTATEMENT:
      return buildWhile(node);
    case JJTIFELSESTATEMENT:
      return buildIfElse(node);
    case JJTBLOCKSTATEMENT:
      return buildMulti(node);
    case JJTRETURNSTATEMENT:
      return buildReturn(node);
    }

    // We should never arrive here.
    throw new InternalError("NodeFactory visited node " + node + " (id " + node.getId() + ")");
  }

  /**
   * Build a DagNode for a PlainStatement SimpleNode. This node is either an assignment
   * or a simple expression; it is deferred to the appropriate subfactory.
   *
   * @param node A JJT node representing a plain statement (assignment or expression)
   * @return A new DAGNode, holding the assignment or expression.
   */
  private DAGNode buildPlain(SimpleNode node) {
    assert node.is(JJTPLAINSTATEMENT);

    SimpleNode childNode = node.jjtGetChild(0);

    if (childNode.is(JJTASSIGNMENT)) {
      return new AssignmentFactory(locals).build(childNode);
    } else {
      return new ExpressionFactory(locals).build(childNode);
    }
  }

  /**
   * Build a DAGNode for a WhileStatement SimpleNode. This node contains a condition DAGExpression
   * node and a DAGNode body node.
   *
   * @param node A JJT node representing a while loop.
   * @return A new DAGWhile, holding the condition and the loop's body.
   */
  private DAGWhile buildWhile(SimpleNode node) {
    assert node.is(JJTWHILESTATEMENT);

    SimpleNode conditionNode = node.jjtGetChild(0);
    SimpleNode bodyNode = node.jjtGetChild(1);

    DAGExpression condition = new ExpressionFactory(locals).build(conditionNode);
    DAGNode loopBody = build(bodyNode);

    // ERROR: Type mismatch in condition expression.
    if (!typematch(booleanDescriptor, condition.getType())) {
      System.err.println("Type mismatch: expected boolean condition, but found "
                         + condition.getType());
      status(MINOR_ERRORS);
    }

    return new DAGWhile(condition, loopBody);
  }

  /**
   * Build a DAGIfElse for an IfElseStatement SimpleNode. This node contains a condition
   * DAGExpression node, a DAGNode for the true branch (then branch), and a DAGNode for the false
   * branch (else branch).
   *
   * @param node A JJT node representing an if else statement.
   * @return A new DAGIfElse, holding the condition and two bodies.
   */
  private DAGIfElse buildIfElse(SimpleNode node) {
    assert node.is(JJTIFELSESTATEMENT);

    SimpleNode conditionNode = node.jjtGetChild(0);
    SimpleNode thenNode = node.jjtGetChild(1);
    SimpleNode elseNode = node.jjtGetChild(2);

    DAGExpression condition = new ExpressionFactory(locals).build(conditionNode);
    DAGNode thenBody = build(thenNode);
    DAGNode elseBody = build(elseNode);

    // ERROR: Type mismatch in condition expression.
    if (!typematch(booleanDescriptor, condition.getType())) {
      System.err.println("Type mismatch: expected boolean condition, but found "
                         + condition.getType());
      status(MINOR_ERRORS);
    }

    return new DAGIfElse(condition, thenBody, elseBody);
  }

  /**
   * Build a DAGMulti for a BlockStatement SimpleNode. This node contains a possibly empty list of
   * SimpleNode comprising its scoped body.
   *
   * @param node A JJT node representing a block statement.
   * @return A new DAGMulti, holding the array of DAGNodes.
   */
  private DAGMulti buildMulti(SimpleNode node) {
    assert node.is(JJTBLOCKSTATEMENT);

    int numNodes = node.jjtGetNumChildren();  // possibly 0
    DAGNode[] nodes = new DAGNode[numNodes];

    for (int i = 0; i < numNodes; ++i) {
      SimpleNode child = node.jjtGetChild(i);
      nodes[i] = build(child);
    }

    return new DAGMulti(nodes);
  }

  /**
   * Build a DAGReturn for a ReturnStatement SimpleNode. This node contains the returned expression.
   *
   * @param node A JJT node representing a return statement with an expression.
   * @return A new DAGReturnExpression node.
   */
  private DAGReturnExpression buildReturn(SimpleNode node) {
    assert node.is(JJTRETURNSTATEMENT);

    SimpleNode expressionNode = node.jjtGetChild(0);

    DAGExpression returned = new ExpressionFactory(locals).build(expressionNode);

    TypeDescriptor expected = locals.getFunction().getReturnType();

    // ERROR: Type mismatch: Invalid return type.
    if (!typematch(expected, returned.getType())) {
      System.err.println("Type mismatch: expected type " + expected
                         + " for return expression, but found " + returned.getType());
      status(MINOR_ERRORS);
    }

    return new DAGReturnExpression(returned);
  }

  /**
   * Build a simple DAGVoidReturn SimpleNode for methods without a return statement, to attach at
   * the end of the method body.
   *
   * @return A new DAGVoidReturn node.
   */
  private DAGVoidReturn buildVoidReturn() {
    return new DAGVoidReturn();
  }

  /**
   * @return The simple node of type MethodBody which is the child of this function node.
   */
  private SimpleNode getMethodBodyNode(SimpleNode methodNode) {
    assert methodNode.is(JJTMETHODDECLARATION) || methodNode.is(JJTMAINDECLARATION);

    SimpleNode bodyNode;

    if (methodNode.is(JJTMETHODDECLARATION)) {
      assert !locals.getFunction().isStatic();  // method
      bodyNode = methodNode.jjtGetChild(3);
    } else {
      assert locals.getFunction().isStatic();  // main
      bodyNode = methodNode.jjtGetChild(1);
    }

    return bodyNode;
  }

  /**
   * @return The simple node of type ReturnStatement which is the child of this function node.
   */
  private SimpleNode getReturnStatementNode(SimpleNode methodNode) {
    assert methodNode.is(JJTMETHODDECLARATION) || methodNode.is(JJTMAINDECLARATION);

    SimpleNode returnNode = null;

    if (methodNode.is(JJTMETHODDECLARATION)) {
      assert !locals.getFunction().isStatic();  // method
      returnNode = methodNode.jjtGetChild(4);
    } else {
      assert locals.getFunction().isStatic();  // main
    }

    return returnNode;
  }
}
