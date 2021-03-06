package compiler.symbols;

import compiler.symbols.FunctionSignature;

public abstract class JavaCallableDescriptor extends CallableDescriptor implements JavaFunction {
  protected JavaCallableDescriptor(String name, ClassDescriptor parent,
                                   FunctionSignature signature) {
    super(name, parent, null, signature);
  }

  protected JavaCallableDescriptor(String name, ClassDescriptor parent, TypeDescriptor returnType,
                                   FunctionSignature signature) {
    super(name, parent, returnType, signature);
  }

  @Override
  public boolean isJMM() {
    return false;
  }

  public void deduceReturnType(TypeDescriptor returnType) {
    this.returnType = returnType;
  }
}
