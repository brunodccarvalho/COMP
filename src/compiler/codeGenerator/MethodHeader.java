package compiler.codeGenerator;

import compiler.symbols.JMMCallableDescriptor;

public class MethodHeader extends JVMInst {
    /**
     * 1: method signature
     * 2: method stack and locals array size
     * 3: method body
     */
    public static String METHOD = "\n\n.method public ?\n?\n?\n.end method";

    private Method method;
    private MethodSignature methodSignature;
    private MethodStackLocals methodStackLocals;
    
    MethodHeader (Method method, JMMCallableDescriptor methodDescriptor)
    {
        this.method = method;
        this.methodSignature = new MethodSignature(method, methodDescriptor, false);
        this.methodStackLocals = new MethodStackLocals(method);
    }

    @Override
    public String toString()
    {
        return subst(MethodHeader.METHOD, this.methodSignature.toString(), this.methodStackLocals.toString());
    }

}