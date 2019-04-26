package compiler.symbols;

import java.util.Objects;

/**
 * A descriptor for a JMM class's main method.
 */
public class JMMMainDescriptor extends BaseFunctionDescriptor {
  private final JMMClassDescriptor parent;
  private final String paramName;

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
    assert parent != null && paramName != null && !parent.hasMain();
    this.parent = parent;
    this.paramName = paramName;
    parent.setMain(this);
  }

  /**
   * @return The name assigned to the parameter with type String[]
   */
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
  public boolean isStatic() {
    return true;
  }

  @Override
  public JMMClassDescriptor getParentClass() {
    return parent;
  }

  @Override
  public VariableDescriptor resolve(String name) {
    return parent.resolveStatic(name);
  }

  @Override
  public String toString() {
    return "main(String[] " + paramName + ")";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Objects.hash(paramName, parent);
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!super.equals(obj)) return false;
    if (!(obj instanceof JMMMainDescriptor)) return false;
    JMMMainDescriptor other = (JMMMainDescriptor) obj;
    return Objects.equals(paramName, other.paramName) && Objects.equals(parent, other.parent);
  }
}
