package compiler.dag;

import compiler.symbols.FunctionSignature;
import compiler.symbols.JavaCallableDescriptor;
import compiler.symbols.CallableDescriptor;
import compiler.symbols.ClassDescriptor;
import compiler.symbols.TypeDescriptor;

public abstract class DAGCall extends DAGExpression {
  final String methodName;
  final DAGExpression[] arguments;
  final FunctionSignature original;
  final CallableDescriptor callable;

  // Unresolved return type for propagation
  DAGCall(String methodName, FunctionSignature original, DAGExpression[] arguments) {
    assert methodName != null && original != null && arguments != null;
    assert original.getNumParameters() == arguments.length;
    this.methodName = methodName;
    this.original = original;
    this.arguments = arguments;
    this.callable = null;
  }

  // Proper constructor
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
  }

  /**
   * @return true if this call is a static method call, false otherwise.
   *         instanceof on DAGMethodCall / DAGStaticCall can be used as well.
   */
  public abstract boolean isStatic();

  /**
   * @return The Class this method belongs to.
   *         If the method is static, its being invoked on this class.
   *         If the method is not static, its being invoked on an object of this class.
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
    return callable == null ? null : callable.getReturnType();
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

  // ****** Package internals for ExpressionFactory

  boolean isNotDeduced() {
    return callable != null && callable.getReturnType() == null
        && callable instanceof JavaCallableDescriptor;
  }

  FunctionSignature getOriginalSignature() {
    return original;
  }

  void deduce(TypeDescriptor returnType) {
    assert callable instanceof JavaCallableDescriptor;
    ((JavaCallableDescriptor) callable).deduceReturnType(returnType);
  }
}
