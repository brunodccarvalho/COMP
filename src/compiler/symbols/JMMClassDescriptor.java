package compiler.symbols;

import java.util.HashMap;

import compiler.FunctionSignature;

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
  private final HashMap<String, MemberVariableDescriptor> members;
  private final HashMap<String, HashMap<FunctionSignature, MethodDescriptor>> methods;
  private StaticMethodDescriptor main;
  private final ClassDescriptor superClass;

  /**
   * Creates a new (mutable) JMM Class descriptor. An instance of this class is
   * created at the start of the compilation of one input JMM file. It is then
   * populated with the discovered data members and member methods.
   */
  public JMMClassDescriptor(String name) {
    super(name);
    this.members = new HashMap<>();
    this.methods = new HashMap<>();
    this.superClass = null;
  }

  /**
   * Creates a new (mutable) JMM Class descriptor with a super class.
   */
  public JMMClassDescriptor(String name, ClassDescriptor superClass) {
    super(name);
    this.members = new HashMap<>();
    this.methods = new HashMap<>();
    this.superClass = superClass;
  }

  /**
   * Returns true if this class has a data member identified by name.
   */
  public boolean hasMember(String name) {
    return members.containsKey(name);
  }

  /**
   * Returns true if this class has at least one member method identified by name.
   */
  public boolean hasMethod(String name) {
    return methods.containsKey(name);
  }

  /**
   * Returns true if this class has at least one member method identified by name
   * and matching the corresponding signature.
   */
  public boolean hasMethod(String name, FunctionSignature signature) {
    HashMap<FunctionSignature, MethodDescriptor> map = methods.get(name);
    if (map == null)
      return false;
    if (signature.isComplete())
      return map.containsKey(signature);
    else
      for (FunctionSignature candidate : map.keySet())
        if (FunctionSignature.matches(candidate, signature))
          return true;
    return false;
  }

  /**
   * Returns the data member identified by name. Possibly null.
   */
  public MemberVariableDescriptor getMember(String name) {
    return members.get(name);
  }

  /**
   * Returns the member method identified by name and matching the complete
   * signature provided.
   */
  public MethodDescriptor getMethod(String name, FunctionSignature signature) {
    HashMap<FunctionSignature, MethodDescriptor> map = methods.get(name);
    if (map == null)
      return null;
    else {
      assert signature.isComplete();
      return map.get(signature);
    }
  }

  /**
   * Add a new data member to this class.
   */
  public boolean addMember(MemberVariableDescriptor var) {
    return members.putIfAbsent(var.getName(), var) == null;
  }

  /**
   * Add a new member method to this class.
   */
  public boolean addMethod(MethodDescriptor method) {
    return methods.computeIfAbsent(method.getName(), name -> new HashMap<>()).putIfAbsent(method.getSignature(),
        method) == null;
  }

  /**
   * Resolve a name within this class's data members; if not found, searches in
   * the superclass.
   *
   * Returns the Descriptor found, or null if not found.
   */
  public Descriptor resolve(String name) {
    MemberVariableDescriptor var = members.get(name);
    if (var != null)
      return var;
    else if (superClass != null)
      return superClass.resolve(name);
    else
      return null;
  }

  /**
   * Resolve a static name within this class's data members; there are none, so it
   * is deferred to the superclass if there is one.
   *
   * Unless non-JMM superclasses are allowed, this method will always return null.
   */
  public Descriptor resolveStatic(String name) {
    if (superClass != null)
      return superClass.resolveStatic(name);
    else
      return null;
  }
}
