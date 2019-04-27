package compiler.dag;

import java.util.Objects;

import compiler.FunctionSignature;
import compiler.symbols.ClassDescriptor;
import compiler.symbols.TypeDescriptor;

public class DAGStaticCall extends DAGCall {
  protected ClassDescriptor classDescriptor;

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

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Objects.hash(classDescriptor);
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!super.equals(obj)) return false;
    if (!(obj instanceof DAGStaticCall)) return false;
    DAGStaticCall other = (DAGStaticCall) obj;
    return Objects.equals(classDescriptor, other.classDescriptor);
  }
}
