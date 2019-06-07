package compiler.codeGenerator;

import compiler.symbols.VariableDescriptor;
import compiler.dag.DAGParameter;

/**
 * LoadParameter
 */
public class LoadParameter extends MethodBodyContent {

    private VariableDescriptor variableDescriptor;
    private Integer variableIndex;

    LoadParameter(Function function, DAGParameter member)
    {
        super(function);
        this.variableDescriptor = member.getVariable();
        this.variableIndex = this.function.variablesIndexes.get(variableDescriptor);
    }

    @Override
    public String toString()
    {
        if(variableIndex == null) { // class field
            return "";
        }
        String variableType = variableDescriptor.getType().toString();
        String regexLoad = CodeGeneratorConstants.load.get(variableType);
        if(regexLoad == null)
            regexLoad = Load.LOADADDRESS;
        return subst(regexLoad, String.valueOf(variableIndex));

    }
    
}