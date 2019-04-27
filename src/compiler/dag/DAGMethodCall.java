package compiler.dag;

import compiler.FunctionSignature;
import compiler.symbols.ClassDescriptor;
import compiler.symbols.TypeDescriptor;

public class DAGMethodCall extends DAGCall {
  DAGExpression expression;

  DAGMethodCall(DAGExpression expression, String methodName, FunctionSignature signature) {
    super(methodName, signature);
    this.expression = expression;
  }

  DAGMethodCall(DAGExpression expression, String methodName, FunctionSignature signature,
                TypeDescriptor returnType) {
    super(methodName, signature, returnType);
    this.expression = expression;
  }

  @Override
  public boolean isStatic() {
    return false;
  }

  @Override
  public ClassDescriptor getCallClass() {
    return (ClassDescriptor) expression.getType();
  }
}
