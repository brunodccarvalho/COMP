package compiler.codeGenerator;

import compiler.dag.DAGVariable;
import compiler.symbols.VariableDescriptor;

public class Store extends MethodBodyContent {
    
    /**
     * 1: index of the variable
     */
    public static String STOREADDRESS = "\n\tastore ?";
    private VariableDescriptor variableDescriptor;
    private Integer variableIndex;

    Store(Function function, DAGVariable variable)
    {
        super(function);
        this.variableDescriptor = variable.getVariable();
        this.variableIndex = this.function.variablesIndexes.get(this.variableDescriptor);
    }

    @Override
    public String toString()
    {
        if(this.variableIndex == null) { // class field
            return "";
        }
        String variableType = variableDescriptor.getType().toString();
        String instruction = CodeGeneratorConstants.store.get(variableType);
        if(instruction == null)
            instruction = Store.STOREADDRESS;
        return subst(instruction, String.valueOf(variableIndex));
    }
    
}