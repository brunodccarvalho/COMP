package compiler.symbols;

import java.util.HashSet;

import compiler.FunctionSignature;

public abstract class JMMCallableDescriptor extends CallableDescriptor implements JMMFunction {
  protected final ParameterDescriptor[] parameters;

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

  protected JMMCallableDescriptor(String name, JMMClassDescriptor parent, TypeDescriptor returnType,
                                  FunctionSignature signature, String[] paramNames) {
    super(name, parent, returnType, signature);
    assert signature.isComplete();
    if (paramNames == null) paramNames = new String[0];
    assert paramNames.length == this.signature.getNumParameters();
    assert validateParameterNames(paramNames);

    this.parameters = new ParameterDescriptor[paramNames.length];
    for (int i = 0; i < paramNames.length; ++i) {
      TypeDescriptor paramType = signature.getParameterType(i);
      String paramName = paramNames[i];
      this.parameters[i] = new ParameterDescriptor(paramType, paramName, this, i);
    }
  }

  public ParameterDescriptor[] getParameters() {
    return parameters.clone();
  }

  @Override
  public JMMClassDescriptor getParentClass() {
    return (JMMClassDescriptor) classDescriptor;
  }

  @Override
  public boolean isJMM() {
    return true;
  }

  @Override
  public boolean hasParameter(String name) {
    for (ParameterDescriptor param : parameters)
      if (param.getName().equals(name)) return true;
    return false;
  }

  @Override
  public ParameterDescriptor getParameter(String name) {
    for (ParameterDescriptor param : parameters)
      if (param.getName().equals(name)) return param;
    return null;
  }

  @Override
  public TypeDescriptor getParameterType(String name) {
    for (ParameterDescriptor param : parameters)
      if (param.getName().equals(name)) return param.getType();
    return null;
  }

  @Override
  public VariableDescriptor resolve(String name) {
    VariableDescriptor var = getParameter(name);
    if (var != null)
      return var;
    else if (isStatic())
      return getParentClass().resolveStatic(name);
    else
      return getParentClass().resolve(name);
  }

  @Override
  public String toString() {
    String[] strings = new String[parameters.length];
    for (int i = 0; i < parameters.length; ++i) strings[i] = parameters[i].toString();
    return returnType + " " + functionName + "(" + String.join(", ", strings) + ")";
  }
}
