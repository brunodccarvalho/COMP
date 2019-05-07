package compiler.dag;

import compiler.symbols.MemberDescriptor;

public class DAGMember extends DAGVariable {
  DAGMember(MemberDescriptor var) {
    super(var);
  }

  @Override
  public MemberDescriptor getVariable() {
    return (MemberDescriptor) var;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return obj instanceof DAGMember && this == obj;
  }
}
