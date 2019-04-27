package compiler.dag;

import compiler.symbols.ClassDescriptor;
import compiler.symbols.TypeDescriptor;

public class DAGNewClass extends DAGNew {
  protected ClassDescriptor classDescriptor;

  DAGNewClass(ClassDescriptor classDescriptor) {
    this.classDescriptor = classDescriptor;
  }

  public ClassDescriptor getClassDescriptor() {
    return classDescriptor;
  }

  @Override
  public ClassDescriptor getType() {
    return classDescriptor;
  }
}
