package compiler.symbols;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

import compiler.FunctionSignature;

/**
 * Base class of all function descriptors. It has an associated signature, a
 * name, and names for each of its parameters, which may be necessary when
 * compiling its body.
 *
 * This descriptor does not know of its own function body, however. The
 * signature and name provided in the constructors cannot be changed, so this
 * class is essentially immutable.
 */
public class FunctionDescriptor extends BaseFunctionDescriptor {
  private final TypeDescriptor returnType;
  private final FunctionSignature signature;
  private final ParameterDescriptor[] parameters;

  /**
   * Ensures there are no two parameters with the same name and no null
   * parameters.
   *
   * @param names The array of names to validate
   * @return True iff the array has no duplicate names and no null values.
   */
  public static boolean validateParameterNames(String[] names) {
    HashSet<String> set = new HashSet<>();
    for (String name : names) {
      assert name != null;
      if (set.contains(name)) return false;
      set.add(name);
    }
    return true;
  }

  /**
   * Creates a new function descriptor with a deduced return type, complete
   * signature and names for each of the parameters.
   *
   * @param name      The function's name.
   * @param ret       The function's return type.
   * @param signature The function's complete signature.
   * @param params    The names of the function's parameters.
   */
  public FunctionDescriptor(String name, TypeDescriptor ret, FunctionSignature signature,
                            String[] params) {
    super(name);

    if (params == null) params = new String[0];

    assert signature != null && signature.isComplete()
        && signature.getNumParameters() == params.length;

    this.returnType = ret;
    this.signature = signature;

    // Check that no argument names are repeated.
    assert validateParameterNames(params);

    // Create the variable descriptors arrays for the signature.
    this.parameters = new ParameterDescriptor[params.length];
    for (int i = 0; i < params.length; ++i) {
      TypeDescriptor typei = signature.getParameterType(i);
      this.parameters[i] = new ParameterDescriptor(typei, params[i], this, i);
    }
  }

  /**
   * @return this function's parameter signature.
   */
  public FunctionSignature getSignature() {
    return signature;
  }

  /**
   * @return this function's return type.
   */
  public TypeDescriptor getReturnType() {
    return returnType;
  }

  @Override
  public boolean hasParameter(String name) {
    for (ParameterDescriptor var : parameters)
      if (var.getName().equals(name)) return true;
    return false;
  }

  @Override
  public boolean hasParameters() {
    return parameters.length > 0;
  }

  @Override
  public int getNumParameters() {
    return signature.getNumParameters();
  }

  /**
   * @param index The index of the parameter
   * @return the type of the parameter at the given position.
   */
  public TypeDescriptor getParameterType(int index) {
    return signature.getParameterType(index);
  }

  /**
   * @return an array with the types of all parameters.
   */
  public TypeDescriptor[] getParameterTypes() {
    return signature.getParameterTypes().clone();
  }

  /**
   * @param index The index of the parameter
   * @return the parameter at the given position.
   */
  public ParameterDescriptor getParameter(int index) {
    return parameters[index];
  }

  /**
   * @return an array with all the parameters.
   */
  public ParameterDescriptor[] getParameters() {
    return parameters.clone();
  }

  /**
   * @param name The name of the parameter
   * @return the parameter descriptor for the given name.
   */
  public ParameterDescriptor getParameter(String name) {
    for (ParameterDescriptor var : parameters)
      if (var.getName().equals(name)) return var;
    return null;
  }

  /**
   * Checks whether an invocation of a method with the same name, with the given
   * (presumably incomplete) signature and given return type could be an
   * invocation of this function.
   *
   * @param signature  The deduced invocation signature
   * @param returnType The deduced invocation return type
   * @return true if it might be an invocation of this method, false otherwise.
   */
  public boolean matches(FunctionSignature signature, TypeDescriptor returnType) {
    return (returnType == null || this.returnType.equals(returnType))
        && FunctionSignature.matches(this.signature, signature);
  }

  @Override
  public VariableDescriptor resolve(String name) {
    return getParameter(name);
  }

  @Override
  public String toString() {
    StringBuilder string = new StringBuilder();
    string.append(returnType).append(' ').append(functionName);
    string.append('(');
    if (parameters.length > 0) {
      string.append(parameters[0]);
      for (int i = 1; i < parameters.length; ++i) string.append(", ").append(parameters[i]);
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
    int result = super.hashCode();
    result = prime * result + Arrays.hashCode(parameters);
    result = prime * result + Objects.hash(returnType, signature);
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!super.equals(obj)) return false;
    if (!(obj instanceof FunctionDescriptor)) return false;
    FunctionDescriptor other = (FunctionDescriptor) obj;
    return Arrays.equals(parameters, other.parameters)
        && Objects.equals(returnType, other.returnType)
        && Objects.equals(signature, other.signature);
  }
}
