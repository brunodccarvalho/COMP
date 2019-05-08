package compiler.codeGenerator;

/**
 * ClassHeader
 * 
 */
public class ClassHeader extends BaseByteCode {
    
    /**
     * 1: name of the class
     */
    public static String CLASSNAME = ".class public ?";

    String className;

    ClassHeader() {
        this.regexReplace=ClassHeader.CLASSNAME;
        this.className=CodeGenerator.singleton.classDescriptor.getClassName();
    }

    @Override
    public String toString()
    {
        return this.subst(this.regexReplace,this.className);
    }
}