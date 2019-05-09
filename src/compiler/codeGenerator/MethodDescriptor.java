package compiler.codeGenerator;

import compiler.symbols.JMMCallableDescriptor;
import compiler.symbols.TypeDescriptor;

public class MethodDescriptor extends JVMInst {
    
    /**
     * 1: type descritor of the method's parameters
     */
    public static String METHODDESCRIPTOR = "?";

    private TypeDescriptor[] typeDescriptors;

    MethodDescriptor(JMMCallableDescriptor method)
    {
        this.typeDescriptors = method.getParameterTypes();
    }
    MethodDescriptor(TypeDescriptor[] typeDescriptors)
    {
        this.typeDescriptors = typeDescriptors;
    }

    @Override
    public String toString()
    {
        String methodDescriptor = new String();
        for(TypeDescriptor typeDescriptor: this.typeDescriptors) {  
            String jvmType = subst(MethodDescriptor.METHODDESCRIPTOR, CodeGeneratorConstants.getType(typeDescriptor)); //TODO verificar o tipo para por ";" no fim caso seja L
            methodDescriptor = methodDescriptor.concat(jvmType);
        }
        return methodDescriptor;
    }
}