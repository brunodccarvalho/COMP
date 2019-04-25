package compiler;

import java.util.Arrays;

import compiler.symbols.TypeDescriptor;

public class FunctionSignature {
  private TypeDescriptor[] parameterTypes;

  public static boolean matches(FunctionSignature s1, FunctionSignature s2) {
    if (s1.getNumParameters() != s2.getNumParameters()) return false;

    int numParameters = s1.getNumParameters();
    for (int i = 0; i < numParameters; ++i) {
      TypeDescriptor t1 = s1.getParameterType(i);
      TypeDescriptor t2 = s2.getParameterType(i);
      if (t1 != null && t2 != null && !t1.equals(t2)) return false;
    }

    return true;
  }

  public FunctionSignature(TypeDescriptor[] params) {
    this.parameterTypes = params == null ? new TypeDescriptor[0] : params;
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

  public TypeDescriptor[] getParameterTypes() {
    return parameterTypes;
  }

  public boolean isComplete() {
    for (TypeDescriptor type : parameterTypes)
      if (type == null) return false;
    return true;
  }

  @Override
  public String toString() {
    if (parameterTypes.length == 0) return "()";
    StringBuilder string = new StringBuilder();
    string.append('(');
    string.append(parameterTypes[0]);
    for (int i = 1; i < parameterTypes.length; ++i) string.append(", ").append(parameterTypes[i]);
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
    result = prime * result + Arrays.hashCode(parameterTypes);
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof FunctionSignature)) return false;
    FunctionSignature other = (FunctionSignature) obj;
    return Arrays.equals(parameterTypes, other.parameterTypes);
  }
}
