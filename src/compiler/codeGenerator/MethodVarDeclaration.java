package compiler.codeGenerator;

import compiler.symbols.JMMMethodDescriptor;
import compiler.symbols.LocalDescriptor;

import java.util.HashMap;
/**
 * MethodVarDeclaration
 */
public class MethodVarDeclaration {

    private Method belongs;

    MethodVarDeclaration(Method belongs,JMMMethodDescriptor method)
    {
        this.belongs=belongs;
        HashMap<String, LocalDescriptor> locals = CodeGenerator.singleton.symbolsTable.getFunctionLocals(method).getVariables();
        int arrayIndex = this.belongs.numberParam;
        for(LocalDescriptor local : locals.values()) {
            if(!this.belongs.variablesIndexes.containsKey(local)) {
                this.belongs.variablesIndexes.put(local, arrayIndex);
                arrayIndex++;
                this.belongs.numberLocals++;
            }
        }
    }

}