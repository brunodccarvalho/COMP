package compiler.dag;

import compiler.symbols.TypeDescriptor;
import jjt.SimpleNode;

interface ExpressionResolverInterface {
  /**
   * Verifies that the given DAGExpression node is, in fact, of the
   * given type, possibly modifying it if its type is unknown, and
   * issuing a diagnostic if the type is known but incorrect.
   *
   * @param expression The DAGExpression being verified.
   * @param type       The type being asserted as the type of dagNode from a parent expression.
   * @param node       The underlying SimpleNode instance.
   * @return The DAGExpression node; possibly modified; possibly a new one altogether.
   */
  abstract DAGExpression assertType(DAGExpression expression, TypeDescriptor type, SimpleNode node);

  /**
   * Asserts that the given DAGExpression node is a statement of its own. The only valid
   * JMM expressions which are valid Java statements are DAGNewClass and DAGCall. Everything
   * else is incorrect and deserves a diagnostic.
   *
   * Top level function calls with unknown return type are asserted to be of return
   * type void, so that they do not push anything to the stack.
   *
   * @param expression The DAGExpression which is a standalone statement.
   * @param node       The underlying SimpleNode instance.
   * @return The DAGExpression node; possibly modified; possibly a new one altogether.
   */
  abstract DAGExpression assertStatement(DAGExpression expression, SimpleNode node);
}
