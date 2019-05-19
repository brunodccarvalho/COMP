package compiler.codeGenerator;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import compiler.modules.CompilationData;
import compiler.symbols.JMMMainDescriptor;
import compiler.symbols.VariableDescriptor;

public class Main extends Function {

    private int localsSize;

    Main(JMMMainDescriptor main, CompilationData data) {
        super(main, data);
        this.localsSize = 999;
    }

    private String initializeVars() {
        String varsInit = new String();
        String assignmentString = "\n\ticonst_0\n\tistore ";
        Iterator<Entry<VariableDescriptor, Integer>> it = this.variablesIndexes.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            VariableDescriptor vd = (VariableDescriptor)(entry.getKey());
            if(vd.getType().isPrimitive())
                varsInit = varsInit.concat(assignmentString + (int)entry.getValue());
        }
        return varsInit;
    }

    @Override
    public String toString() {

        String methodStack = subst(CodeGeneratorConstants.STACK, String.valueOf(localsSize));
        String methodLocals = subst(CodeGeneratorConstants.LOCALS, String.valueOf(localsSize));
        String varsInit = this.initializeVars();
        String mainBodyRegex = subst(CodeGeneratorConstants.MAIN, methodStack, methodLocals, varsInit, methodBody.toString());
        return mainBodyRegex;
    }
}