package compiler.dag;

import compiler.symbols.MemberDescriptor;

public class DAGMember extends DAGVariable {
  int localTableIndex;

  DAGMember(MemberDescriptor var) {
    super(var);
  }

  @Override
  public MemberDescriptor getVariable() {
    return (MemberDescriptor) var;
  }
}
