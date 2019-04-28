package compiler.symbols;

import java.util.HashMap;
import java.util.HashSet;

import compiler.FunctionSignature;

/**
 * Represents a class being parsed by this compiler: it must obey the rules of
 * JMM and be parseable by our compiler, which means:
 *
 * * All data members are package-private.
 *
 * * All member functions are public and have non-void return type.
 *
 * * There is at most one static function, with name 'main'.
 *
 * * There may be a super class. We don't deal with this yet.
 */
public class JMMClassDescriptor extends ClassDescriptor {
  private final ClassDescriptor superClass;
  private final HashMap<String, MemberDescriptor> members;
  private final HashMap<String, HashMap<FunctionSignature, JMMMethodDescriptor>> methods;
  private JMMMainDescriptor main;

  /**
   * Creates a new (mutable) JMM Class descriptor. An instance of this class is
   * created at the start of the compilation of one input JMM file. It is then
   * populated with the discovered data members and member methods.
   *
   * @param name The name of this JMM class
   */
  public JMMClassDescriptor(String name) {
    super(name);
    this.superClass = null;
    this.members = new HashMap<>();
    this.methods = new HashMap<>();
  }

  /**
   * Creates a new (mutable) JMM Class descriptor with a super class.
   *
   * @param name       The name of this JMM class
   * @param superClass This class's super class, from the extends clause
   */
  public JMMClassDescriptor(String name, ClassDescriptor superClass) {
    super(name);
    this.superClass = superClass;
    this.members = new HashMap<>();
    this.methods = new HashMap<>();
  }

  @Override
  public boolean hasMethod(String name) {
    return methods.containsKey(name);
  }

  @Override
  public boolean hasMethod(String name, FunctionSignature signature) {
    HashMap<FunctionSignature, JMMMethodDescriptor> map = methods.get(name);
    if (map == null) return false;
    if (signature.isComplete())
      return map.containsKey(signature);
    else
      for (FunctionSignature candidate : map.keySet())
        if (FunctionSignature.matches(candidate, signature)) return true;
    return false;
  }

  @Override
  public boolean hasReturning(String name, FunctionSignature signature, TypeDescriptor returnType) {
    HashMap<FunctionSignature, JMMMethodDescriptor> map = methods.get(name);
    if (map == null) return false;
    if (signature.isComplete())
      return map.containsKey(signature);
    else
      for (JMMMethodDescriptor candidate : map.values())
        if (candidate.returning(signature, returnType)) return true;
    return false;
  }

  @Override
  public boolean hasStaticMethod(String name) {
    return false;
  }

  @Override
  public boolean hasStaticMethod(String name, FunctionSignature signature) {
    return false;
  }

  @Override
  public boolean hasStaticReturning(String name, FunctionSignature signature,
                                    TypeDescriptor returnType) {
    return false;
  }

  /**
   * @param name      The name of the method
   * @param signature The signature of the method (its parameter types)
   * @return The method descriptor, if it exists.
   */
  public JMMMethodDescriptor getMethod(String name, FunctionSignature signature) {
    HashMap<FunctionSignature, JMMMethodDescriptor> map = methods.get(name);
    if (map == null)
      return null;
    else {
      assert signature.isComplete();
      return map.get(signature);
    }
  }

  /**
   * Add a new member method to this class.
   *
   * @param method The new member method
   */
  void addMethod(JMMMethodDescriptor method) {
    assert method.getSignature().isComplete();

    HashMap<FunctionSignature, JMMMethodDescriptor> map;
    map = methods.computeIfAbsent(method.getName(), n -> new HashMap<>());

    assert !map.containsKey(method.getSignature());
    map.put(method.getSignature(), method);
  }

  /**
   * @return true if this class has a main method.
   */
  public boolean hasMain() {
    return main != null;
  }

  /**
   * @return The name of the super class
   */
  public String getSuperClassName() {
    if(this.superClass == null) return null;
    return this.superClass.getClassName();
  }

  /**
   * @return The main method of this class.
   */
  public JMMMainDescriptor getMain() {
    return main;
  }

  /**
   * Set the class's main static method.
   *
   * @param main The JMM class's main method
   */
  void setMain(JMMMainDescriptor main) {
    assert main != null && this.main == null;
    this.main = main;
  }

  /**
   * @param name The name of the data member
   * @return true if this class has a data member with the given name.
   */
  public boolean hasMember(String name) {
    return members.containsKey(name);
  }

  /**
   * @param name The data member name
   * @return The data member identified by name.
   */
  public MemberDescriptor getMember(String name) {
    return members.get(name);
  }

  /**
   * Add a new data member to this class.
   *
   * @param var The new data member variable
   */
  void addMember(MemberDescriptor var) {
    assert !members.containsKey(var.getName());
    members.put(var.getName(), var);
  }

  @Override
  public VariableDescriptor resolve(String name) {
    MemberDescriptor var = members.get(name);
    if (var != null)
      return var;
    else if (superClass != null)
      return superClass.resolve(name);
    else
      return null;
  }

  @Override
  public VariableDescriptor resolveStatic(String name) {
    if (superClass != null)
      return superClass.resolveStatic(name);
    else
      return null;
  }

  /**
   * Convenience methods to list all members.
   */
  public MemberDescriptor[] getMembersList() {
    return members.values().toArray(new MemberDescriptor[members.size()]);
  }

  /**
   * Convenience methods to list all methods.
   */
  public JMMMethodDescriptor[] getMethodsList() {
    HashSet<JMMMethodDescriptor> methodsSet = new HashSet<>();
    for (HashMap<FunctionSignature, JMMMethodDescriptor> map : methods.values()) {
      methodsSet.addAll(map.values());
    }
    return methodsSet.toArray(new JMMMethodDescriptor[methodsSet.size()]);
  }
}
