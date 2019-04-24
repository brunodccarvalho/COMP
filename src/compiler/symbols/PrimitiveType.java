package compiler.symbols;

/**
 * Up for refactoring any day.
 */
public class PrimitiveType extends TypeDescriptor {
  public enum Type {
    INT("int"), INTARRAY("int[]"), BOOLEAN("boolean");

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

  public PrimitiveType(Type primitive) {
    super(primitive.toString());
    this.primitive = primitive;

  }

  public boolean isPrimitive() {
    return true;
  }

  public boolean isClass() {
    return false;
  }

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
    return null;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((primitive == null) ? 0 : primitive.hashCode());
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
    PrimitiveType other = (PrimitiveType) obj;
    if (primitive != other.primitive)
      return false;
    return true;
  }
}
