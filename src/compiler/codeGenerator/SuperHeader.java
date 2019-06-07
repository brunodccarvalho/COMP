package compiler.codeGenerator;

/**
 * SuperHeader
 */
public class SuperHeader extends JVMInst {
    /**
     * 1: name of the super class
     */
    public static String SUPERNAME = ".super ?";
    public static String DEFAULTSUPER = "java/lang/Object";
    private final String superName;

    public SuperHeader (String superName) {
        this.superName = superName == null ? SuperHeader.DEFAULTSUPER : superName;
    }

    @Override
    public String toString()
    {
        return JVMInst.subst(SuperHeader.SUPERNAME, this.superName);
    }
}
