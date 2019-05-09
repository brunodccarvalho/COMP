package compiler.codeGenerator;

import compiler.symbols.JMMCallableDescriptor;
import compiler.symbols.ParameterDescriptor;

public class MethodParam {

    private Method belongs;

    MethodParam (Method belongs, JMMCallableDescriptor method) {
        this.belongs=belongs;
        ParameterDescriptor[] parameters = method.getParameters();
        for(ParameterDescriptor parameter: parameters) {
            if(!this.belongs.variablesIndexes.containsKey(parameter)) {
                this.belongs.variablesIndexes.put(parameter, this.belongs.numberParam);
                this.belongs.numberParam++;
            }
        }
    }
    
}