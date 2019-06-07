package compiler.symbols;

/**
 * The descriptor for a generic Java class, whose contents are unknown.
 */
public class UnknownClassDescriptor extends ClassDescriptor {
  /**
   * Creates a new Java Class descriptor. The members and methods of this class
   * are completely unknown.
   *
   * @param name The name of the unknown class
   */
  public UnknownClassDescriptor(String name) {
    super(name);
  }

  @Override
  public ClassDescriptor getSuper() {
    return null;
  }

  @Override
  public boolean extendsClass(ClassDescriptor otherClass) {
    assert otherClass != null;
    return false;
  }

  @Override
  public boolean hasMethod(String name) {
    return true;
  }

  @Override
  public boolean hasMethod(String name, FunctionSignature signature) {
    return true;
  }

  @Override
  public boolean hasReturning(String name, FunctionSignature signature, TypeDescriptor returnType) {
    return true;
  }

  @Override
  public boolean hasStaticMethod(String name) {
    return true;
  }

  @Override
  public boolean hasStaticMethod(String name, FunctionSignature signature) {
    return true;
  }

  @Override
  public boolean hasStaticReturning(String name, FunctionSignature signature,
                                    TypeDescriptor returnType) {
    return true;
  }

  @Override
  public VariableDescriptor resolve(String name) {
    return null;
  }

  @Override
  public VariableDescriptor resolveStatic(String name) {
    return null;
  }

  @Override
  public Deduction deduce(String name, FunctionSignature signature) {
    if (!signature.isComplete()) return new Deduction(null, false, true);
    JavaMethodDescriptor callable;
    callable = new JavaMethodDescriptor(name, this, null, signature);
    return new Deduction(callable, true, false);
  }

  @Override
  public Deduction deduceStatic(String name, FunctionSignature signature) {
    if (!signature.isComplete()) return new Deduction(null, false, true);
    JavaStaticMethodDescriptor callable;
    callable = new JavaStaticMethodDescriptor(name, this, null, signature);
    return new Deduction(callable, true, false);
  }
}
