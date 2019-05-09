package compiler.codeGenerator;

import compiler.symbols.JMMMethodDescriptor;
import compiler.symbols.TypeDescriptor;
import java.util.HashMap;
/**
 * MethodDescriptor
 */
public class MethodDescriptor extends JVMInst {
    
    /**
     * 1: type descritor of the method's parameters
     */
    public static String METHODDESCRIPTOR = "?";

    private TypeDescriptor[] typeDescriptors;

    MethodDescriptor(JMMMethodDescriptor method)
    {
        this.regexReplace=MethodDescriptor.METHODDESCRIPTOR;
        this.typeDescriptors = method.getParameterTypes();
    }
    MethodDescriptor(TypeDescriptor[] typeDescriptors)
    {
        this.regexReplace=MethodDescriptor.METHODDESCRIPTOR;
        this.typeDescriptors = typeDescriptors;
    }

    @Override
    public String toString()
    {
        String methodDescriptor = new String();
        for(TypeDescriptor typeDescriptor: this.typeDescriptors) {  
            String jvmType = subst(this.regexReplace, CodeGeneratorConstants.getType(typeDescriptor)); //TODO verificar o tipo para por ";" no fim caso seja L
            methodDescriptor = methodDescriptor.concat(jvmType);
        }
        return methodDescriptor;
    }
    
}