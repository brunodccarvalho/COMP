package compiler.symbols;

public class VoidDescriptor extends TypeDescriptor {
  VoidDescriptor() {
    super("void");
  }

  @Override
  public boolean isPrimitive() {
    return false;
  }

  @Override
  public boolean isClass() {
    return false;
  }

  @Override
  public boolean isArray() {
    return false;
  }

  @Override
  public String getBytecodeString() {
    return "V";
  }
}
