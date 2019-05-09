package compiler.codeGenerator;

/**
 * Constructors
 */
public class Constructors extends JVMInst {

    private static String DEFAULTINITIALIZER = "\n.method public <init>()V\n\taload_0\n\tinvokenonvirtual java/lang/Object/<init>()V\n\treturn\n.end method";

    public Constructors () {}

    @Override
    public String toString()
    {
        return JVMInst.subst(Constructors.DEFAULTINITIALIZER);
    }
    
}