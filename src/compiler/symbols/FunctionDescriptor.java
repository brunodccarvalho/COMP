package compiler.symbols;

import java.util.Arrays;
import java.util.HashSet;

class FunctionDescriptor extends Descriptor {
  private String functionName;
  private FunctionSignature signature;
  private VariableDescriptor[] parameters;

  private void validateParameterNames(VariableDescriptor[] params) {
    HashSet<String> names = new HashSet<>();
    for (VariableDescriptor var : params) {
      if (names.contains(var.getName()))
        throw new IllegalArgumentException(
            "Repeated parameter name (" + var.getName() + ") in function " + functionName);
      names.add(var.getName());
    }
  }

  public FunctionDescriptor(String name, TypeDescriptor ret, VariableDescriptor[] params) {
    assert name != null && ret != null;
    if (params == null)
      params = new VariableDescriptor[0];

    this.functionName = name;
    this.parameters = params;

    // Check that no argument names are repeated.
    validateParameterNames(params);

    // Create the type descriptors arrays for the signature.
    TypeDescriptor[] paramTypes = new TypeDescriptor[params.length];
    for (int i = 0; i < params.length; ++i)
      paramTypes[i] = params[i].getType();

    this.signature = new FunctionSignature(ret, paramTypes);
    assert this.signature.isComplete();
  }

  public FunctionSignature getSignature() {
    return signature;
  }

  public TypeDescriptor getReturnType() {
    return signature.getReturnType();
  }

  public String getName() {
    return functionName;
  }

  public boolean hasParameters() {
    return parameters.length > 0;
  }

  public int getNumParameters() {
    return signature.getNumParameters();
  }

  public TypeDescriptor getParameterType(int index) {
    return signature.getParameterType(index);
  }

  public TypeDescriptor[] getParameterTypes() {
    return signature.getParameterTypes();
  }

  public VariableDescriptor getParameter(int index) {
    return parameters[index];
  }

  public VariableDescriptor[] getParameters() {
    return parameters;
  }

  public boolean hasParameter(String name) {
    for (VariableDescriptor var : parameters)
      if (var.getName().equals(name))
        return true;
    return false;
  }

  public VariableDescriptor getParameter(String name) {
    for (VariableDescriptor var : parameters)
      if (var.getName().equals(name))
        return var;
    return null;
  }

  public Descriptor resolve(String name) {
    return getParameter(name);
  }

  @Override
  public String toString() {
    StringBuilder string = new StringBuilder();
    string.append(signature.getReturnType()).append(' ').append(functionName).append('(');
    if (parameters.length > 0) {
      string.append(parameters[0]);
      for (int i = 1; i < parameters.length; ++i)
        string.append(", ").append(parameters[i]);
    }
    string.append(')');
    return string.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((functionName == null) ? 0 : functionName.hashCode());
    result = prime * result + Arrays.hashCode(parameters);
    result = prime * result + ((signature == null) ? 0 : signature.hashCode());
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
    FunctionDescriptor other = (FunctionDescriptor) obj;
    if (functionName == null) {
      if (other.functionName != null)
        return false;
    } else if (!functionName.equals(other.functionName))
      return false;
    if (!Arrays.equals(parameters, other.parameters))
      return false;
    if (signature == null) {
      if (other.signature != null)
        return false;
    } else if (!signature.equals(other.signature))
      return false;
    return true;
  }
}
