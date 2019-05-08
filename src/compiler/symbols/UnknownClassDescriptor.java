package compiler.symbols;

import compiler.symbols.FunctionSignature;

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
  public TypeDescriptor getReturnType(String name, FunctionSignature signature) {
    return null;
  }

  @Override
  public TypeDescriptor getReturnTypeStatic(String name, FunctionSignature signature) {
    return null;
  }
}
