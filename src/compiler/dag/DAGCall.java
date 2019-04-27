package compiler.dag;

import compiler.FunctionSignature;
import compiler.symbols.ClassDescriptor;
import compiler.symbols.TypeDescriptor;

public abstract class DAGCall extends DAGExpression {
  protected String methodName;
  protected FunctionSignature signature;
  protected TypeDescriptor returnType;

  DAGCall(String methodName, FunctionSignature signature) {
    this.methodName = methodName;
    this.signature = signature;
    this.returnType = null;
  }

  DAGCall(String methodName, FunctionSignature signature, TypeDescriptor returnType) {
    this.methodName = methodName;
    this.signature = signature;
    this.returnType = returnType;
  }

  public abstract boolean isStatic();

  public abstract ClassDescriptor getCallClass();

  public String getMethodName() {
    return methodName;
  }

  public FunctionSignature getSignature() {
    return signature;
  }

  @Override
  public TypeDescriptor getType() {
    return returnType;
  }
}
