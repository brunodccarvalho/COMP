package compiler.dag;

import java.util.Arrays;
import java.util.Objects;

import compiler.FunctionSignature;
import compiler.symbols.ClassDescriptor;
import compiler.symbols.TypeDescriptor;

public abstract class DAGCall extends DAGExpression {
  protected final String methodName;
  protected final FunctionSignature signature;
  protected final TypeDescriptor returnType;
  protected final DAGExpression[] arguments;

  DAGCall(String methodName, FunctionSignature signature, DAGExpression[] arguments) {
    assert methodName != null && signature != null && arguments != null;
    this.methodName = methodName;
    this.signature = signature;
    this.returnType = null;
    this.arguments = arguments;
  }

  DAGCall(String methodName, FunctionSignature signature, TypeDescriptor returnType,
          DAGExpression[] arguments) {
    assert methodName != null && signature != null && returnType != null && arguments != null;
    this.methodName = methodName;
    this.signature = signature;
    this.returnType = returnType;
    this.arguments = arguments;
  }

  /**
   * @return true if this call is a static method call, false otherwise.
   */
  public abstract boolean isStatic();

  /**
   * @return The Class this method belongs to.
   */
  public abstract ClassDescriptor getCallClass();

  /**
   * @return This method's name.
   */
  public String getMethodName() {
    return methodName;
  }

  /**
   * @return This method's signature.
   */
  public FunctionSignature getSignature() {
    return signature;
  }

  /**
   * @return Number of arguments to this function call.
   */
  public int getNumArguments() {
    return arguments.length;
  }

  /**
   * @return Argument list of this function call.
   */
  public DAGExpression[] getArguments() {
    return arguments.clone();
  }

  public String[] getArgumentsNames() {
    for(DAG)
  }

  /**
   * @return Argument #i of this function call.
   */
  public DAGExpression getArgument(int i) {
    return arguments[i];
  }

  @Override
  public TypeDescriptor getType() {
    return returnType;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder string = new StringBuilder();
    string.append(methodName).append('(');
    if (arguments.length > 0) {
      string.append(arguments[0]);
      for (int i = 1; i < arguments.length; ++i) {
        string.append(", ").append(arguments[i]);
      }
    }
    string.append(')');
    return string.toString();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(arguments);
    result = prime * result + Objects.hash(methodName, returnType, signature);
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return obj instanceof DAGCall && this == obj;
  }
}
