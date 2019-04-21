package compiler.symbols;

import java.util.HashMap;

/**
 * Represents a class being parsed by this compiler: it must obey the rules of
 * JMM and be parseable by our compiler, which means:
 *
 * * All data members are package-protected.
 *
 * * All member functions are public and have non-void return type.
 *
 * * There is at most one static function, with name 'main'.
 *
 * * There may be a super class.
 */
public class JMMClassDescriptor extends ClassDescriptor {
  private HashMap<String, MemberVariableDescriptor> members;
  private HashMap<String, HashMap<FunctionSignature, MethodDescriptor>> methods;
  private StaticMethodDescriptor main;
  private ClassDescriptor superClass;

  public JMMClassDescriptor(String name) {
    super(name);
  }

  public JMMClassDescriptor(String name, ClassDescriptor superClass) {
    super(name);
    this.superClass = superClass;
  }

  public boolean hasMember(String name) {
    return members.containsKey(name);
  }

  public boolean hasMethod(String name) {
    return members.containsKey(name);
  }

  public boolean hasMethod(String name, FunctionSignature signature) {
    HashMap<FunctionSignature, MethodDescriptor> map = methods.get(name);
    if (map == null)
      return false;
    else
      return map.containsKey(signature);
  }

  public MemberVariableDescriptor getMember(String name) {
    return members.get(name);
  }

  // Search with matches...
  public MethodDescriptor getMethod(String name, FunctionSignature signature) {
    HashMap<FunctionSignature, MethodDescriptor> map = methods.get(name);
    if (map == null)
      return null;
    else
      return map.get(signature);
  }

  public boolean addMember(MemberVariableDescriptor var) {
    return members.putIfAbsent(var.getName(), var) == null;
  }

  public boolean addMethod(MethodDescriptor method) {
    return methods.computeIfAbsent(method.getName(), name -> new HashMap<>()).putIfAbsent(method.getSignature(),
        method) == null;
  }

  public Descriptor resolve(String name) {
    MemberVariableDescriptor var = members.get(name);
    if (var != null)
      return var;
    else if (superClass != null)
      return superClass.resolve(name);
    else
      return null;
  }

  public Descriptor resolveStatic(String name) {
    if (superClass != null)
      return superClass.resolveStatic(name);
    else
      return null;
  }
}
