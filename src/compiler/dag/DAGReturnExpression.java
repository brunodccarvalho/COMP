package compiler.dag;

import compiler.symbols.TypeDescriptor;

public class DAGReturnExpression extends DAGReturn {
  protected final DAGExpression returnExpression;

  DAGReturnExpression(DAGExpression returnExpression) {
    assert returnExpression != null;
    this.returnExpression = returnExpression;
  }

  /**
   * @return The int return expression.
   */
  public DAGExpression getExpression() {
    return returnExpression;
  }

  @Override
  public TypeDescriptor getType() {
    return returnExpression.getType();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */

  @Override
  public String toString() {
    return "return " + returnExpression + ";\n";
  }
}
