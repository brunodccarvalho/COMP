package compiler.symbols;

import compiler.symbols.FunctionSignature;

public class JavaMethodDescriptor extends JavaCallableDescriptor implements MethodFunction {
  protected JavaMethodDescriptor(String name, ClassDescriptor parent, FunctionSignature signature) {
    super(name, parent, signature);
  }

  protected JavaMethodDescriptor(String name, ClassDescriptor parent, TypeDescriptor returnType,
                                 FunctionSignature signature) {
    super(name, parent, returnType, signature);
  }

  @Override
  public boolean isStatic() {
    return false;
  }
}
