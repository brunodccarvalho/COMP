package compiler.codeGenerator;

import compiler.symbols.JMMCallableDescriptor;
import compiler.symbols.LocalDescriptor;
import java.util.HashMap;

public class MethodVarDeclaration {

    private Method method;

    MethodVarDeclaration(Method method, JMMCallableDescriptor methodDescriptor)
    {
        this.method = method;
        HashMap<String, LocalDescriptor> locals = method.data.localsMap.get(methodDescriptor).getVariables();
        int arrayIndex = this.method.numberParam;
        for(LocalDescriptor local : locals.values()) {
            if(!this.method.variablesIndexes.containsKey(local)) {
                this.method.variablesIndexes.put(local, arrayIndex);
                arrayIndex++;
                this.method.numberLocals++;
            }
        }
    }

}