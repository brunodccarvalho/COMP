package compiler.symbols;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Base class of all _declared_ function descriptors. It has an associated
 * signature, a name, and names for each of its parameters, which may be
 * necessary when compiling its body.
 *
 * This descriptor does not know of its own function body, however. The
 * signature and name provided in the constructors cannot be changed, so this
 * class is essentially immutable.
 */
public class FunctionDescriptor extends Descriptor {
  private final String functionName;
  private final FunctionSignature signature;
  private final ParameterDescriptor[] parameters;

  private void validateParameterNames(String[] names) {
    HashSet<String> set = new HashSet<>();
    for (String name : names) {
      if (set.contains(name))
        throw new IllegalArgumentException("Repeated parameter name (" + name + ") in function " + functionName);
      set.add(name);
    }
  }

  public FunctionDescriptor(String name, FunctionSignature signature, String[] params) {
    assert name != null && signature != null && signature.isComplete();
    if (params == null)
      params = new String[0];
    assert signature.getNumParameters() == params.length;

    this.functionName = name;
    this.signature = signature;

    // Check that no argument names are repeated.
    validateParameterNames(params);

    // Create the variable descriptors arrays for the signature.
    this.parameters = new ParameterDescriptor[params.length];
    for (int i = 0; i < params.length; ++i) {
      TypeDescriptor typei = signature.getParameterType(i);
      this.parameters[i] = new ParameterDescriptor(typei, params[i], this, i);
    }
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

  public ParameterDescriptor getParameter(int index) {
    return parameters[index];
  }

  public ParameterDescriptor[] getParameters() {
    return parameters;
  }

  public boolean hasParameter(String name) {
    for (ParameterDescriptor var : parameters)
      if (var.getName().equals(name))
        return true;
    return false;
  }

  public ParameterDescriptor getParameter(String name) {
    for (ParameterDescriptor var : parameters)
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
