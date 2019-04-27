package compiler.dag;

import compiler.FunctionSignature;
import compiler.symbols.ClassDescriptor;
import compiler.symbols.TypeDescriptor;

public class DAGStaticCall extends DAGCall {
  protected ClassDescriptor classDescriptor;

  DAGStaticCall(ClassDescriptor classDescriptor, String methodName, FunctionSignature signature) {
    super(methodName, signature);
    this.classDescriptor = classDescriptor;
  }

  DAGStaticCall(ClassDescriptor classDescriptor, String methodName, FunctionSignature signature,
                TypeDescriptor returnType) {
    super(methodName, signature, returnType);
    this.classDescriptor = classDescriptor;
  }

  @Override
  public boolean isStatic() {
    return true;
  }

  @Override
  public ClassDescriptor getCallClass() {
    return classDescriptor;
  }
}
