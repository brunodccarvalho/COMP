package compiler.codeGenerator;

public final class CodeGeneratorConstants {

    public static String DEFAULTSUPER = "java/lang/Object";
    public static String DEFAULTINITIALIZER = ".method public <init>()V\naload_0\ninvokenonvirtual java/lang/Object/<init>()V\nreturn\n.end method";
    public static String INITIALIZERNAME = "<init>";
    /**
     * 1: name of the class
     */
    public static String CLASSNAME = ".class public ?";
    /**
     * 1: name of the super class
     */
    public static String SUPERNAME = ".super ?";
    /**
     * 1: type descritor of the method's parameters
     */
    public static String METHODDESCRIPTOR = "?;";
    /**
     * 1: name of the class to which the method belongs
     * 2: name of the method
     * 3: descriptor of the method
     * 4: return type
     */
    public static String METHODSIGNATURE = "?/?/(?)/?";
    /**
     * 1: method signature
     * 2: method body
     */
    public static String METHOD = ".method public ?\n?return\n.end method";
}