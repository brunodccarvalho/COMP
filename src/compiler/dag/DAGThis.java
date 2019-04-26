package compiler.dag;

import compiler.symbols.LocalDescriptor;

public class DAGThis extends DAGVariable {
  DAGThis(LocalDescriptor thisVariable) {
    super(thisVariable);
  }
}
