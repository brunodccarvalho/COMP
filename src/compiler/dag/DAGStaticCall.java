package compiler.dag;

import compiler.symbols.FunctionSignature;
import compiler.symbols.ClassDescriptor;
import compiler.symbols.TypeDescriptor;

public class DAGStaticCall extends DAGCall {
  final ClassDescriptor classDescriptor;

  DAGStaticCall(ClassDescriptor classDescriptor, String methodName, FunctionSignature signature,
                DAGExpression[] arguments) {
    super(methodName, signature, arguments);
    assert classDescriptor != null;
    this.classDescriptor = classDescriptor;
  }

  DAGStaticCall(ClassDescriptor classDescriptor, String methodName, FunctionSignature signature,
                TypeDescriptor returnType, DAGExpression[] arguments) {
    super(methodName, signature, returnType, arguments);
    assert classDescriptor != null;
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

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return classDescriptor.toString() + super.toString();
  }
}
