package compiler.codeGenerator;

import java.util.Iterator;
import java.util.Map;
import compiler.symbols.JMMCallableDescriptor;
import compiler.symbols.VariableDescriptor;
import java.util.Map.Entry;

public class MethodHeader extends JVMInst {
    /**
     * 1: method signature
     * 2: method stack and locals array size
     * 3: method variables initialization
     * 4: method body
     */
    public static String METHOD = "\n\n.method public ?\n?\n?\n?\n.end method";

    private Method method;
    private MethodSignature methodSignature;
    private MethodStackLocals methodStackLocals;
    
    MethodHeader (Method method, JMMCallableDescriptor methodDescriptor)
    {
        this.method = method;
        this.methodSignature = new MethodSignature(method, methodDescriptor, false);
        this.methodStackLocals = new MethodStackLocals(method);
    }


    private String initializeVars() {
        String varsInit = new String();
        String assignmentString = "\n\ticonst_0\n\tistore ";
        Iterator<Entry<VariableDescriptor, Integer>> it = this.method.variablesIndexes.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            VariableDescriptor vd = (VariableDescriptor)(entry.getKey());
            if(vd.getType().isPrimitive())
                varsInit = varsInit.concat(assignmentString + (int)entry.getValue());
        }
        return varsInit;
    }

    @Override
    public String toString()
    {
        String varsInit = this.initializeVars();
        return subst(MethodHeader.METHOD, this.methodSignature.toString(), this.methodStackLocals.toString(), varsInit);
    }

}