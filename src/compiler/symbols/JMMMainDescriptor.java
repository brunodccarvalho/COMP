package compiler.symbols;

/**
 * A descriptor for a JMM class's main method.
 */
public class JMMMainDescriptor extends Descriptor {
  private final JMMClassDescriptor parent;
  private String paramName;

  public JMMMainDescriptor(JMMClassDescriptor parent, String paramName) {
    assert parent != null && paramName != null;
    this.parent = parent;
    this.paramName = paramName;
  }

  public String getName() {
    return "main";
  }

  public String getParameterName() {
    return paramName;
  }

  public boolean hasParameter(String name) {
    return paramName.equals(name);
  }

  public Descriptor resolve(String name) {
    return parent.resolveStatic(name);
  }

  @Override
  public String toString() {
    return "main(String[] " + paramName + ')';
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((paramName == null) ? 0 : paramName.hashCode());
    result = prime * result + ((parent == null) ? 0 : parent.hashCode());
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
