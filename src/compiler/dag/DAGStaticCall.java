package compiler.dag;

import compiler.symbols.FunctionSignature;
import compiler.symbols.CallableDescriptor;
import compiler.symbols.ClassDescriptor;

public class DAGStaticCall extends DAGCall {
  final ClassDescriptor classDescriptor;

  DAGStaticCall(ClassDescriptor classDescriptor, String methodName, FunctionSignature signature,
                DAGExpression[] arguments) {
    super(methodName, signature, arguments);
    assert classDescriptor != null;
    this.classDescriptor = classDescriptor;
  }

  DAGStaticCall(String methodName, FunctionSignature signature, CallableDescriptor callable,
                DAGExpression[] arguments) {
    super(methodName, signature, callable, arguments);
    assert callable.isStatic();
    this.classDescriptor = callable.getParentClass();
  }

  @Override
  public boolean isStatic() {
    return true;
  }

  @Override
  public ClassDescriptor getCallClass() {
    return classDescriptor;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return classDescriptor.toString() + super.toString();
  }
}
