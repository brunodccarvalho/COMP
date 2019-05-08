package compiler.codeGenerator;

/**
 * SuperHeader
 */
public class SuperHeader extends BaseByteCode{
    /**
     * 1: name of the super class
     */
    public static String SUPERNAME = ".super ?";
    public static String DEFAULTSUPER = "java/lang/Object";

    private String superName;

    SuperHeader () {
        this.superName=CodeGenerator.singleton.classDescriptor.getSuperClassName();
        if(this.superName==null)
        {
            this.superName=SuperHeader.DEFAULTSUPER;
        }
        this.regexReplace=SuperHeader.SUPERNAME;
    }

    @Override
    public String toString()
    {
        return this.subst(this.regexReplace, this.superName);
    }
}