package compiler.symbols;

import compiler.FunctionSignature;

public abstract class JavaCallableDescriptor extends CallableDescriptor implements JavaFunction {
  protected JavaCallableDescriptor(String name, ClassDescriptor parent, TypeDescriptor returnType,
                                   FunctionSignature signature) {
    super(name, parent, returnType, signature);
  }

  @Override
  public boolean isJMM() {
    return false;
  }
}
