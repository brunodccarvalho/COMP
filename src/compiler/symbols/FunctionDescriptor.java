package compiler.symbols;

import java.util.Arrays;
import java.util.HashSet;

import compiler.FunctionSignature;

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
  private final TypeDescriptor returnType;
  private final FunctionSignature signature;
  private final ParameterDescriptor[] parameters;

  /**
   * Ensures there are no two parameters with the same name.
   */
  private void validateParameterNames(String[] names) {
    HashSet<String> set = new HashSet<>();
    for (String name : names) {
      if (set.contains(name))
        throw new IllegalArgumentException("Repeated parameter name (" + name + ") in function " + functionName);
      set.add(name);
    }
  }

  /**
   * Creates a new function descriptor with a deduced return type, complete
   * signature and names for each of the parameters. The function's body is
   * presumably known.
   *
   * @param name      The function's name.
   * @param ret       The function's return type.
   * @param signature The function's complete signature.
   * @param params    The names of the function's parameters.
   */
  public FunctionDescriptor(String name, TypeDescriptor ret, FunctionSignature signature, String[] params) {
    assert name != null && ret != null && signature != null && signature.isComplete();
    if (params == null)
      params = new String[0];
    assert signature.getNumParameters() == params.length;

    this.functionName = name;
    this.returnType = ret;
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

  /**
   * Returns this function's parameter signature.
   */
  public FunctionSignature getSignature() {
    return signature;
  }

  /**
   * Returns this function's return type.
   */
  public TypeDescriptor getReturnType() {
    return returnType;
  }

  /**
   * Returns this function's name.
   */
  public String getName() {
    return functionName;
  }

  /**
   * Returns true if this function takes at least one argument.
   */
  public boolean hasParameters() {
    return parameters.length > 0;
  }

  /**
   * Returns the number of parameters of this function.
   */
  public int getNumParameters() {
    return signature.getNumParameters();
  }

  /**
   * Returns the type of the parameter at the given position.
   */
  public TypeDescriptor getParameterType(int index) {
    return signature.getParameterType(index);
  }

  /**
   * Returns an array with the types of all parameters.
   */
  public TypeDescriptor[] getParameterTypes() {
    return signature.getParameterTypes();
  }

  /**
   * Returns the parameter at the given position.
   */
  public ParameterDescriptor getParameter(int index) {
    return parameters[index];
  }

  /**
   * Returns an array with all the parameters.
   */
  public ParameterDescriptor[] getParameters() {
    return parameters;
  }

  /**
   * Returns true if this function has a parameter with the given name.
   */
  public boolean hasParameter(String name) {
    for (ParameterDescriptor var : parameters)
      if (var.getName().equals(name))
        return true;
    return false;
  }

  /**
   * Returns the parameter descriptor for the given name.
   */
  public ParameterDescriptor getParameter(String name) {
    for (ParameterDescriptor var : parameters)
      if (var.getName().equals(name))
        return var;
    return null;
  }

  /**
   * Returns the parameter descriptor for the given name.
   *
   * Should be overriden in descendant classes.
   */
  public Descriptor resolve(String name) {
    return getParameter(name);
  }

  @Override
  public String toString() {
    StringBuilder string = new StringBuilder();
    string.append(returnType).append(' ').append(functionName);
    string.append('(');
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
    result = prime * result + ((returnType == null) ? 0 : returnType.hashCode());
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
    if (returnType == null) {
      if (other.returnType != null)
        return false;
    } else if (!returnType.equals(other.returnType))
      return false;
    if (signature == null) {
      if (other.signature != null)
        return false;
    } else if (!signature.equals(other.signature))
      return false;
    return true;
  }
}
