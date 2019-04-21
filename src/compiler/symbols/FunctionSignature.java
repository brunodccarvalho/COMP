package compiler.symbols;

import java.util.Arrays;

// TODO: handle void return types as null?

/**
 * Class representing a function type: its return type and its parameters'
 * types, in order. An instance of this class is mutable: we may start off only
 * knowning some things about the represented function's signature, and we'll
 * discover more about it as we go along through the source file's code. Imagine
 * for example a function class like
 *
 * io.printf(format, Thread.getId());
 *
 * First, we fail to resolve name 'io' through the symbol table chain, so we
 * must deduce it to an external class name. So we add to the class name table
 * (if we hadn't done that already). Second, we deduce it has a static function
 * called 'printf' taking two arguments and unknown return type, possibly even
 * void. But we do not know their types just yet -- we don't have access to the
 * function declaration. So we add this function 'printf' to the class io's
 * static member function table with an incomplete signature. Third, the first
 * argument -- format -- is resolved successfully, to say a parameter variable
 * of type String. This means that we can deduce the first parameter of the
 * printf function has type String -- and we can deduce the first parameter type
 * conclusively. The second argument, however, follows the same pattern from the
 * start: Thread is not resolved successfully, so we deduce it to an external
 * class name; then getId is one of its static functions taking no arguments,
 * and its return value is unknown, so we cannot use it to deduce printf's
 * second parameter type. Either way, we accept this statement as is.
 *
 * This is why a function signature is mutable: so we may add information to it
 * as we discover it implicitly throughout the source file.
 */
public class FunctionSignature {
  private TypeDescriptor returnType;
  private TypeDescriptor[] parameterTypes;

  public FunctionSignature(TypeDescriptor ret) {
    assert ret != null;
    this.returnType = ret;
    this.parameterTypes = new TypeDescriptor[0];
  }

  public FunctionSignature(TypeDescriptor ret, int numParams) {
    assert ret != null && numParams >= 0;
    this.returnType = ret;
    this.parameterTypes = new TypeDescriptor[numParams];
  }

  public FunctionSignature(TypeDescriptor ret, TypeDescriptor[] params) {
    assert ret != null;
    this.returnType = ret;
    this.parameterTypes = params == null ? new TypeDescriptor[0] : params;
  }

  public FunctionSignature(int numParams) {
    assert numParams >= 0;
    this.parameterTypes = new TypeDescriptor[numParams];
  }

  public FunctionSignature(TypeDescriptor[] params) {
    this.parameterTypes = params == null ? new TypeDescriptor[0] : params;
  }

  public TypeDescriptor getReturnType() {
    return returnType;
  }

  public void setReturnType(TypeDescriptor type) {
    returnType = type;
  }

  public boolean hasParameters() {
    return parameterTypes.length > 0;
  }

  public int getNumParameters() {
    return parameterTypes.length;
  }

  public TypeDescriptor getParameterType(int index) {
    return parameterTypes[index];
  }

  public void setParameterType(int index, TypeDescriptor type) {
    parameterTypes[index] = type;
  }

  public TypeDescriptor[] getParameterTypes() {
    return parameterTypes;
  }

  public boolean isComplete() {
    if (returnType == null)
      return false;
    for (TypeDescriptor type : parameterTypes)
      if (type == null)
        return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuilder string = new StringBuilder();
    string.append(returnType).append('(');
    if (parameterTypes.length > 0) {
      string.append(parameterTypes[0]);
      for (int i = 1; i < parameterTypes.length; ++i)
        string.append(',').append(parameterTypes[i]);
    }
    string.append(')');
    return string.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(parameterTypes);
    result = prime * result + ((returnType == null) ? 0 : returnType.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    FunctionSignature other = (FunctionSignature) obj;
    if (!Arrays.equals(parameterTypes, other.parameterTypes))
      return false;
    if (returnType == null) {
      if (other.returnType != null)
        return false;
    } else if (!returnType.equals(other.returnType))
      return false;
    return true;
  }
}
