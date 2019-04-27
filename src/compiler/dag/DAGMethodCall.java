package compiler.dag;

import java.util.Objects;

import compiler.FunctionSignature;
import compiler.symbols.ClassDescriptor;
import compiler.symbols.TypeDescriptor;

public class DAGMethodCall extends DAGCall {
  DAGExpression expression;

  DAGMethodCall(DAGExpression expression, String methodName, FunctionSignature signature,
                DAGExpression[] arguments) {
    super(methodName, signature, arguments);
    assert expression != null;
    this.expression = expression;
  }

  DAGMethodCall(DAGExpression expression, String methodName, FunctionSignature signature,
                TypeDescriptor returnType, DAGExpression[] arguments) {
    super(methodName, signature, returnType, arguments);
    assert expression != null;
    this.expression = expression;
  }

  public DAGExpression getObjectExpression() {
    return expression;
  }

  @Override
  public boolean isStatic() {
    return false;
  }

  @Override
  public ClassDescriptor getCallClass() {
    return (ClassDescriptor) expression.getType();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return expression.toString() + super.toString();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(expression);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof DAGMethodCall)) return false;
    DAGMethodCall other = (DAGMethodCall) obj;
    return Objects.equals(expression, other.expression);
  }
}
