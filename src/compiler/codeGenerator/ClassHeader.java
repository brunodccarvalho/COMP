package compiler.codeGenerator;

/**
 * ClassHeader
 * 
 */
public class ClassHeader extends JVMInst {
    
    /**
     * 1: name of the class
     */
    public static String CLASSNAME = ".class public ?";
    private String className;

    public ClassHeader(String className) {
        this.className = className;
    }

    @Override
    public String toString()
    {
        return JVMInst.subst(ClassHeader.CLASSNAME, className);
    }
}