package compiler.dag;

import java.util.Objects;

import compiler.symbols.ClassDescriptor;

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

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof DAGNewClass)) return false;
    DAGNewClass other = (DAGNewClass) obj;
    return Objects.equals(classDescriptor, other.classDescriptor);
  }
}
