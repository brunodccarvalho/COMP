package compiler.dag;

import compiler.symbols.ThisDescriptor;

public class DAGThis extends DAGVariable {
  DAGThis(ThisDescriptor thisVariable) {
    super(thisVariable);
  }
}
