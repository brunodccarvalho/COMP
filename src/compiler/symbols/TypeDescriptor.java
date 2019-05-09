package compiler.symbols;

import java.util.HashMap;
import java.util.Objects;

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

  public static final HashMap<String, TypeDescriptor> typesMap = new HashMap<>();

  // The descriptor of "void"
  public static final VoidDescriptor voidDescriptor = new VoidDescriptor();

  // The descriptor of an unknown type
  public static final PrimitiveDescriptor unknownResolvedType = PrimitiveDescriptor.intDescriptor;

  /**
   * @param name A typename being resolved.
   * @return The TypeDescriptor instance for the given typename, or null if none
   *         exists.
   */
  public static TypeDescriptor get(String name) {
    return typesMap.get(name);
  }

  /**
   * Creates a new instance of UnknownClassDescriptor for this typename if none
   * exists.
   *
   * @param name A typename being resolved or created.
   * @return The TypeDescriptor instance for this typename, possibly just created.
   */
  public static TypeDescriptor getOrCreate(String name) {
    if (typesMap.containsKey(name)) return typesMap.get(name);

    TypeDescriptor classDescriptor = new UnknownClassDescriptor(name);
    assert classDescriptor.isClass();
    return classDescriptor;
  }

  /**
   * @param name A typename being resolved.
   * @return true if the type exists, false otherwise.
   */
  public static boolean exists(String name) {
    return typesMap.containsKey(name);
  }

  /**
   * @param type1 A TypeDescriptor, or null if the type is unknown.
   * @param type2 A TypeDescriptor, or null if the type is unknown.
   * @return true if the type 1 could be an instance of type 2. This will take inheritance in
   *     consideration in the future.
   */
  public static boolean typematch(TypeDescriptor type1, TypeDescriptor type2) {
    return type1 == null || type2 == null || type1 == type2;
  }

  /**
   * Construct a new instance of a TypeDescriptor with the given name.
   *
   * @param name The typename
   */
  protected TypeDescriptor(String name) {
    assert name != null;
    this.name = name;
    if (typesMap.containsKey(name))
      throw new IllegalStateException("Type '" + name + "' already exists");
    typesMap.put(name, this);
  }

  /**
   * @return The typename
   */
  public String getName() {
    return name;
  }

  /**
   * The only primitive types in JMM are int and boolean.
   *
   * If true, this is an instance of PrimitiveDescriptor.
   *
   * @return true if this is a primitive type, false otherwise.
   */
  public abstract boolean isPrimitive();

  /**
   * Almost everything in JMM is a class, including String.
   *
   * If true, this is an instance of ClassDescriptor.
   *
   * @return true if this is a class type, false otherwise.
   */
  public abstract boolean isClass();

  /**
   * The only array type in JMM is int[].
   *
   * If true, this is an instance of PrimitiveDescriptor.
   *
   * @return true if this is an array type, false otherwise.
   */
  public abstract boolean isArray();

  /**
   * The only reference types in JMM are int[] and classes.
   *
   * @return True if this is a reference type, false otherwise.
   */
  public boolean isReference() {
    return isArray() || isClass();
  }

  /**
   * @return The JVM descriptor string for this type.
   */
  public abstract String getBytecodeString();

  @Override
  public String toString() {
    return name;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof TypeDescriptor)) return false;
    TypeDescriptor other = (TypeDescriptor) obj;
    return Objects.equals(name, other.name);
  }
}
