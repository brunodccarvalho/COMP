package compiler.symbols;

import java.util.HashMap;

/**
 * Base class of a type descriptor (class and primitive type descriptors).
 *
 * The type has a name which unequivocally identifies it.
 *
 * This class has a protected constructor and two subclasses:
 * PrimitiveDescriptor and ClassDescriptor. This is so because we do not
 * distinguish between classes and interfaces, and support only integer arrays
 * (int[]).
 *
 * This class holds a list of all 'discovered' typenames and their corresponding
 * type descriptors. The PrimitiveDescriptor class holds three static instances
 * for the int, int array and boolean primitive types supported by this
 * compiler.
 *
 * An existing type can be retrieved with 'get(name)'. An missing type can be
 * created upon retrieval with 'getOrCreate(name)' -- this function will create
 * a new instance of JavaClassDescriptor for the name if it was not found,
 * returning it. A name can be inspected with 'exists(name)' -- returning true
 * if it has an entry in the map.
 */
public abstract class TypeDescriptor extends Descriptor {
  protected final String name;

  private static HashMap<String, TypeDescriptor> typesMap = new HashMap<>();

  public static TypeDescriptor get(String name) {
    return typesMap.get(name);
  }

  public static TypeDescriptor getOrCreate(String name) {
    if (typesMap.containsKey(name))
      return typesMap.get(name);

    TypeDescriptor classDescriptor = new JavaClassDescriptor(name);
    assert classDescriptor.isClass();
    return classDescriptor;
  }

  public static boolean exists(String name) {
    return typesMap.containsKey(name);
  }

  protected TypeDescriptor(String name) {
    assert name != null;
    this.name = name;
    if (typesMap.containsKey(name))
      throw new IllegalStateException("Type '" + name + "' already exists");
    typesMap.put(name, this);
  }

  public String getName() {
    return name;
  }

  public abstract boolean isPrimitive();

  public abstract boolean isClass();

  public abstract boolean isArray();

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
