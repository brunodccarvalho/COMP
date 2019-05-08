package compiler.codeGenerator;

import compiler.symbols.JMMMethodDescriptor;
import compiler.symbols.ParameterDescriptor;

import java.util.HashMap;
/**
 * MethodParam
 */
public class MethodParam {

    private Method belongs;

    MethodParam (Method belongs,JMMMethodDescriptor method) {
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