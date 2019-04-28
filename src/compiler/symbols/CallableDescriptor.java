package compiler.symbols;

import compiler.FunctionSignature;

public abstract class CallableDescriptor extends BaseFunctionDescriptor implements Callable {
  protected final TypeDescriptor returnType;
  protected final FunctionSignature signature;

  protected CallableDescriptor(String name, ClassDescriptor parent, TypeDescriptor returnType,
                               FunctionSignature signature) {
    super(name, parent);
    assert returnType != null && signature != null;
    this.returnType = returnType;
    this.signature = signature;
  }

  @Override
  public boolean hasParameters() {
    return signature.hasParameters();
  }

  @Override
  public int getNumParameters() {
    return signature.getNumParameters();
  }

  @Override
  public TypeDescriptor getReturnType() {
    return returnType;
  }

  @Override
  public boolean hasParameter(int index) {
    return signature.hasParameter(index);
  }

  @Override
  public TypeDescriptor getParameterType(int index) {
    return signature.getParameterType(index);
  }

  @Override
  public TypeDescriptor[] getParameterTypes() {
    return signature.getParameterTypes();
  }

  @Override
  public FunctionSignature getSignature() {
    return signature;
  }

  @Override
  public boolean matches(FunctionSignature deducedSignature) {
    assert deducedSignature != null;
    return FunctionSignature.matches(signature, deducedSignature);
  }

  @Override
  public boolean returns(TypeDescriptor deducedReturnType) {
    assert deducedReturnType != null;
    return returnType == null || deducedReturnType == null || (returnType == deducedReturnType);
  }

  @Override
  public boolean returning(FunctionSignature deducedSignature, TypeDescriptor deducedReturnType) {
    return returns(deducedReturnType) && matches(deducedSignature);
  }
}
