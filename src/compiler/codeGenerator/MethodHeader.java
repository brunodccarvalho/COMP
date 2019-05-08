package compiler.codeGenerator;

import compiler.symbols.JMMMethodDescriptor;
/**
 * MethodHeader
 */
public class MethodHeader extends JVMInst {
    /**
     * 1: method signature
     * 2: method stack and locals array size
     * 3: method body
     */
    public static String METHOD = ".method public ?\n?\n?\n.end method";

    private Method belongs;

    private MethodSignature methodSignature;
    private MethodStackLocals methodStackLocals;
    
    MethodHeader (Method belongs,JMMMethodDescriptor method)
    {
        this.belongs=belongs;
        this.regexReplace=MethodHeader.METHOD;
        
        this.methodSignature = new MethodSignature(method, false);
        this.methodStackLocals = new MethodStackLocals(belongs);
    }

    @Override
    public String toString()
    {
        return subst(this.regexReplace, this.methodSignature.toString(), this.methodStackLocals.toString());
    }

}