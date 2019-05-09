package compiler.symbols;

import compiler.symbols.FunctionSignature;

public class JavaStaticMethodDescriptor extends JavaCallableDescriptor implements StaticFunction {
  protected JavaStaticMethodDescriptor(String name, ClassDescriptor parent,
                                       TypeDescriptor returnType, FunctionSignature signature) {
    super(name, parent, returnType, signature);
  }

  @Override
  public boolean isStatic() {
    return true;
  }
}
