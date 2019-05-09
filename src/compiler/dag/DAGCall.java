package compiler.dag;

import compiler.symbols.FunctionSignature;
import compiler.symbols.CallableDescriptor;
import compiler.symbols.ClassDescriptor;
import compiler.symbols.TypeDescriptor;

public abstract class DAGCall extends DAGExpression {
  final String methodName;
  final DAGExpression[] arguments;
  final FunctionSignature original;
  CallableDescriptor callable;
  TypeDescriptor returnType;

  DAGCall(String methodName, FunctionSignature original, DAGExpression[] arguments) {
    assert methodName != null && original != null && arguments != null;
    assert original.getNumParameters() == arguments.length;
    this.methodName = methodName;
    this.original = original;
    this.arguments = arguments;
    this.callable = null;
    this.returnType = null;
  }

  DAGCall(String methodName, FunctionSignature original, CallableDescriptor callable,
          DAGExpression[] arguments) {
    assert methodName != null && original != null && callable != null && arguments != null;
    assert original.getNumParameters() == arguments.length;
    assert FunctionSignature.matches(original, callable.getSignature());
    assert callable.getSignature().isComplete();
    this.methodName = methodName;
    this.original = original;
    this.arguments = arguments;
    this.callable = callable;
    this.returnType = callable.getReturnType();  // possibly null
  }

  /**
   * @return true if this call is a static method call, false otherwise.
   */
  public abstract boolean isStatic();

  /**
   * @return The Class this method belongs to.
   *         If the method is static, its being invoked on this class.
   */
  public abstract ClassDescriptor getCallClass();

  /**
   * @return This method's name.
   */
  public String getMethodName() {
    return methodName;
  }

  /**
   * @return This method's callable signature.
   */
  public FunctionSignature getSignature() {
    return callable.getSignature();
  }

  /**
   * @return Number of arguments of this function call.
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

  /**
   * @return Argument #i of this function call.
   */
  public DAGExpression getArgument(int i) {
    return arguments[i];
  }

  /**
   * @return The callable.
   */
  public CallableDescriptor getCallable() {
    return callable;
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
    string.append('.').append(methodName).append('(');
    if (arguments.length > 0) {
      TypeDescriptor type = arguments[0].getType();
      string.append('<').append(type == null ? '?' : type).append("> ");
      string.append(arguments[0]);
      for (int i = 1; i < arguments.length; ++i) {
        type = arguments[i].getType();
        string.append(", ");
        string.append('<').append(type == null ? '?' : type).append("> ");
        string.append(arguments[i]);
      }
    }
    string.append(')');
    return string.toString();
  }

  // ****** Package internals for ExpressionResolver

  /**
   * Set the callable CallableDescriptor matching this invocation.
   */
  void setDeducedCallable(CallableDescriptor callable, TypeDescriptor returnType) {
    assert callable.isStatic() == isStatic();
    assert callable.getParentClass() == getCallClass();
    assert FunctionSignature.matches(original, callable.getSignature());
    assert methodName.equals(callable.getName());
    assert callable.getReturnType() == returnType;
    this.callable = callable;
    this.returnType = returnType;
  }

  /**
   * We could not resolve the callable, set the return type so we can keep going.
   */
  void setDeducedReturnType(TypeDescriptor returnType) {
    assert returnType != null;
    this.returnType = returnType;
  }

  /**
   * Set an argument of this invocation, it might have changed after an assertType
   * and an optimize.
   */
  void setArgument(int index, DAGExpression argument) {
    assert index < arguments.length;
    this.arguments[index] = argument;
  }

  /**
   * @return True if this DAGCall has a callable and a return type.
   */
  boolean isDeduced() {
    return returnType != null;
  }

  /**
   * @return This method's original signature.
   */
  FunctionSignature getOriginalSignature() {
    return original;
  }

  /**
   * Unchecked getCallClass()
   */
  abstract TypeDescriptor getCallClassUnchecked();
}
