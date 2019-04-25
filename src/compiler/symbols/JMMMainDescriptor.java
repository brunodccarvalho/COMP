package compiler.symbols;

/**
 * A descriptor for a JMM class's main method.
 */
public class JMMMainDescriptor extends BaseFunctionDescriptor {
  private final JMMClassDescriptor parent;
  private String paramName;

  /**
   * Creates a new main static method descriptor for a JMM class.
   *
   * The parameter name of String[] is just a dummy.
   *
   * @param parent    The JMM class
   * @param paramName The name of the String[] parameter
   */
  public JMMMainDescriptor(JMMClassDescriptor parent, String paramName) {
    super("main");
    assert parent != null && paramName != null;
    this.parent = parent;
    this.paramName = paramName;
  }

  public String getParameterName() {
    return paramName;
  }

  @Override
  public boolean hasParameter(String name) {
    return false;
  }

  @Override
  public boolean hasParameters() {
    return false;
  }

  @Override
  public int getNumParameters() {
    return 1;
  }

  @Override
  public VariableDescriptor resolve(String name) {
    return parent.resolveStatic(name);
  }

  @Override
  public String toString() {
    return "main(String[] " + paramName + ")";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((paramName == null) ? 0 : paramName.hashCode());
    result = prime * result + ((parent == null) ? 0 : parent.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    JMMMainDescriptor other = (JMMMainDescriptor) obj;
    if (paramName == null) {
      if (other.paramName != null)
        return false;
    } else if (!paramName.equals(other.paramName))
      return false;
    if (parent == null) {
      if (other.parent != null)
        return false;
    } else if (!parent.equals(other.parent))
      return false;
    return true;
  }
}
