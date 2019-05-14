package compiler.dag;

import compiler.symbols.FunctionSignature;
import compiler.symbols.CallableDescriptor;
import compiler.symbols.ClassDescriptor;

public class DAGMethodCall extends DAGCall {
  final DAGExpression expression;

  // Unresolved return type for propagation
  DAGMethodCall(DAGExpression expression, String methodName, FunctionSignature signature,
                DAGExpression[] arguments) {
    super(methodName, signature, arguments);
    assert expression != null;
    this.expression = expression;
  }

  // Proper constructor
  DAGMethodCall(DAGExpression expression, String methodName, FunctionSignature signature,
                CallableDescriptor callable, DAGExpression[] arguments) {
    super(methodName, signature, callable, arguments);
    assert expression != null && !callable.isStatic();
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
