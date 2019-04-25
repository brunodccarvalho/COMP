package compiler.symbols;

import static compiler.symbols.PrimitiveDescriptor.Type.*;

import java.util.Objects;

/**
 * Up for refactoring any day.
 */
public class PrimitiveDescriptor extends TypeDescriptor {
  public enum Type {
    INT("int"),
    INTARRAY("int[]"),
    BOOLEAN("boolean");

    private String str;

    Type(String s) {
      this.str = s;
    }

    @Override
    public String toString() {
      return str;
    }
  }

  private final Type primitive;

  // The descriptor of the type "int"
  public static final PrimitiveDescriptor intDescriptor = new PrimitiveDescriptor(INT);

  // The descriptor of the type "int[]"
  public static final PrimitiveDescriptor intArrayDescriptor = new PrimitiveDescriptor(INTARRAY);

  // The descriptor of the type "boolean"
  public static final PrimitiveDescriptor booleanDescriptor = new PrimitiveDescriptor(BOOLEAN);

  /**
   * Constructs a new Primitive type.
   *
   * All instances of this class should be public static fields above.
   */
  private PrimitiveDescriptor(Type primitive) {
    super(primitive.toString());
    this.primitive = primitive;
  }

  @Override
  public boolean isPrimitive() {
    return primitive != Type.INTARRAY;
  }

  @Override
  public boolean isClass() {
    return false;
  }

  @Override
  public boolean isArray() {
    return primitive == Type.INTARRAY;
  }

  @Override
  public String toString() {
    switch (primitive) {
    case INT:
      return "int";
    case INTARRAY:
      return "int[]";
    case BOOLEAN:
      return "boolean";
    }

    // We should never arrive here.
    assert false;
    return null;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Objects.hash(primitive);
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!super.equals(obj)) return false;
    if (!(obj instanceof PrimitiveDescriptor)) return false;
    PrimitiveDescriptor other = (PrimitiveDescriptor) obj;
    return primitive == other.primitive;
  }
}
