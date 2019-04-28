package compiler.codeGenerator;

import java.util.HashMap;

public final class CodeGeneratorConstants {

    public static String DEFAULTSUPER = "java/lang/Object";
    public static String DEFAULTINITIALIZER = ".method public <init>()V\n\taload_0\n\tinvokenonvirtual java/lang/Object/<init>()V\n\treturn\n.end method";
    public static String INITIALIZERNAME = "<init>";
    public static HashMap<String, String> types;
    static {
        types = new HashMap<>();
        types.put("int", "I");
        types.put("boolean", "Z");
        types.put("int[]", "[I");
    }

    /**
     * 1: name of the class
     */
    public static String CLASSNAME = ".class public ?";
    /**
     * 1: name of the super class
     */
    public static String SUPERNAME = ".super ?";

    public static String CLASSTYPE = "L?";
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
    public static String METHODSIGNATURE = "?/?(?)?";
    /**
     * 1: method signature
     * 2: method body
     */
    public static String METHOD = ".method public ?\n?\n\treturn\n.end method";
}