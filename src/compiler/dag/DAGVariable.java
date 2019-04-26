package compiler.dag;

import compiler.symbols.VariableDescriptor;

public class DAGVariable extends DAGTerm {
    private VariableDescriptor var;
  
    DAGIntegerConstant(VariableDescriptor var) {

      this.var = var;
    }
  
    public VariableDescriptor getVar() {
      return var;
    }
  
    @Override
    public TypeDescriptor getType() {
      return var.getType();
    }
  }
