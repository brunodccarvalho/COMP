package compiler.symbols;

import java.util.HashMap;

/**
 * Base class of a type descriptor (class and primitive type descriptors). The
 * type has a name which unequivocally identifies it.
 *
 * TODO
 */
public class TypeDescriptor extends Descriptor {
  protected final String name;

  private static HashMap<String, TypeDescriptor> types = new HashMap<>();

  public static TypeDescriptor get(String name) {
    return types.get(name);
  }

  public static boolean has(String name) {
    return types.containsKey(name);
  }

  protected TypeDescriptor(String name) {
    assert name != null;
    this.name = name;
    if (types.containsKey(name))
      throw new IllegalStateException("Type '" + name + "' already exists");
    types.put(name, this);
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
    TypeDescriptor other = (TypeDescriptor) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
}
