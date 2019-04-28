package compiler.symbols;

import compiler.FunctionSignature;

public class JMMMethodDescriptor extends JMMCallableDescriptor implements MethodFunction {
  public JMMMethodDescriptor(String name, JMMClassDescriptor parent, TypeDescriptor returnType,
                             FunctionSignature signature, String[] paramNames) {
    super(name, parent, returnType, signature, paramNames);
  }

  @Override
  public boolean isStatic() {
    return false;
  }
}
