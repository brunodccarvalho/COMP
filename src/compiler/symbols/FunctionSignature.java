package compiler.symbols;

import java.util.Arrays;

// TODO: handle void return types as null?

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
