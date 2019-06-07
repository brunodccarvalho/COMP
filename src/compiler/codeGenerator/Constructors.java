package compiler.codeGenerator;

import compiler.symbols.ClassDescriptor;

/**
 * Constructors
 */
public class Constructors extends JVMInst {

    private static String DEFAULTINITIALIZER = "\n.method public <init>()V\n\taload_0\n\tinvokenonvirtual ?/<init>()V\n\treturn\n.end method";

    private final String superClass;

    public Constructors (ClassDescriptor superClass) {
        this.superClass = superClass == null ? "java/lang/Object" : superClass.getName();
    }

    @Override
    public String toString()
    {
        return JVMInst.subst(Constructors.DEFAULTINITIALIZER, superClass);
    }

}
