package compiler.codeGenerator;

/**
 * Constructors
 */
public class Constructors extends BaseByteCode {

    public static String DEFAULTINITIALIZER = ".method public <init>()V\n\taload_0\n\tinvokenonvirtual java/lang/Object/<init>()V\n\treturn\n.end method";


    // TODO: other constructors besides the default constructor
    Constructors () {
        this.regexReplace=Constructors.DEFAULTINITIALIZER;
    }

    @Override
    public String toString()
    {
        return this.subst(this.regexReplace);
    }
    
}