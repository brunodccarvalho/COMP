package compiler.dag;

import java.util.Objects;

import compiler.symbols.ClassDescriptor;

public class DAGNewClass extends DAGNew {
  protected final ClassDescriptor classDescriptor;

  DAGNewClass(ClassDescriptor classDescriptor) {
    assert classDescriptor != null;
    this.classDescriptor = classDescriptor;
  }

  public ClassDescriptor getClassDescriptor() {
    return classDescriptor;
  }

  @Override
  public ClassDescriptor getType() {
    return classDescriptor;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return classDescriptor.toString();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(classDescriptor);
  }
}
