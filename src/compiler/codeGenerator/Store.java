package compiler.codeGenerator;

import compiler.dag.DAGVariable;
import compiler.symbols.VariableDescriptor;

import java.util.HashMap;
/**
 * Store
 */
public class Store extends BaseByteCode {
    
    /**
     * 1: index of the variable
     */
    public static String STOREADDRESS = "\tastore ?";

    private VariableDescriptor variableDescriptor;
    private Integer variableIndex;

    Store(DAGVariable variable)
    {
        this.variableDescriptor = variable.getVariable();
        this.variableIndex = CodeGenerator.singleton.variablesIndexes.get(this.variableDescriptor);
    }

    @Override
    public String toString()
    {
        if(this.variableIndex == null) { // class field
            return "";
        }
        String variableType = variableDescriptor.getType().toString();
        this.regexReplace = CodeGeneratorConstants.store.get(variableType);
        if(this.regexReplace == null)
            this.regexReplace = Store.STOREADDRESS;
        return subst(this.regexReplace, String.valueOf(variableIndex+1)) + "\n";

    }
    
}