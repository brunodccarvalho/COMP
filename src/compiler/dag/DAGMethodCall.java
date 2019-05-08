package compiler.dag;

import compiler.symbols.FunctionSignature;
import compiler.symbols.ClassDescriptor;
import compiler.symbols.TypeDescriptor;

public class DAGMethodCall extends DAGCall {
  final DAGExpression expression;

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

  /**
   * @return The object (expression) on which this method is being called.
   */
  public DAGExpression getObjectExpression() {
    return expression;
  }

  @Override
  public boolean isStatic() {
    return false;
  }

  @Override
  public ClassDescriptor getCallClass() {
    assert expression.getType() instanceof ClassDescriptor;
    return (ClassDescriptor) expression.getType();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return expression.toString() + super.toString();
  }
}
