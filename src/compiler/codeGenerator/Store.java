package compiler.codeGenerator;

import compiler.dag.DAGVariable;
import compiler.symbols.VariableDescriptor;

import java.util.HashMap;
/**
 * Store
 */
public class Store extends JVMInst {
    
    /**
     * 1: index of the variable
     */
    public static String STOREADDRESS = "\tastore ?";
    private Function function;
    private VariableDescriptor variableDescriptor;
    private Integer variableIndex;

    Store(Function function, DAGVariable variable)
    {
        this.function = function;
        this.variableDescriptor = variable.getVariable();
        this.variableIndex = function.variablesIndexes.get(this.variableDescriptor);
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
        return subst(instruction, String.valueOf(variableIndex+1)) + "\n";
    }
    
}