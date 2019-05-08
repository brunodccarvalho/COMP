package compiler.codeGenerator;

import compiler.dag.DAGCall;
import compiler.symbols.JMMMethodDescriptor;

/**
 * MethodSignature
 */
public class MethodSignature extends BaseByteCode {

    /**
     * 1: name of the class to which the method belongs 2: name of the method 3:
     * descriptor of the method 4: return type
     */
    public static String METHODSIGNATURE = "?/?(?)?";
    /**
     * 1: name of the method 2: descriptor of the method 3: return type
     */
    public static String METHODSIGNATURENOCLASS = "?(?)?";

    private String methodClass;
    private String methodName;
    private String methodDescriptor;
    private String returnType;

    private boolean includeClassName;

    private boolean dagCall;

    MethodSignature(JMMMethodDescriptor method, boolean includeClassName) {
        this.dagCall = false;
        this.includeClassName = includeClassName;
        this.methodClass = CodeGenerator.singleton.classDescriptor.getClassName();
        this.methodName = method.getName();
        MethodDescriptor descript = new MethodDescriptor(method);
        this.methodDescriptor = descript.toString();
        this.returnType = CodeGeneratorConstants.getType(method.getReturnType());
    }

    MethodSignature(DAGCall methodCall) {
        this.dagCall = true;
        this.methodClass = methodCall.getCallClass().toString();
        this.methodName = methodCall.getMethodName();
        MethodDescriptor descript = new MethodDescriptor(methodCall.getSignature().getParameterTypes());
        this.methodDescriptor = descript.toString();
        if (methodCall.getType() == null) {
            this.returnType = CodeGeneratorConstants.types.get("void");
        } else
            this.returnType = CodeGeneratorConstants.getType(methodCall.getType());

    }

    private String generateMethodSignature(String methodClass, String methodName, String methodDescriptor,
            String returnType) {
        String methodSignature = subst(MethodSignature.METHODSIGNATURE, methodClass, methodName, methodDescriptor,
                returnType);
        methodSignature = methodSignature.replaceAll("\\?", "");
        return methodSignature;
    }

    private String generateMethodSignature(String methodName, String methodDescriptor, String returnType) {
        String methodSignature = subst(MethodSignature.METHODSIGNATURENOCLASS, methodName, methodDescriptor,
                returnType);
        return methodSignature;
    }

    @Override
    public String toString() {
        if (dagCall) {
            return this.generateMethodSignature(methodClass, methodName, methodDescriptor, returnType);
        } else {
            if (this.includeClassName)
                return this.generateMethodSignature(this.methodClass, this.methodName, this.methodDescriptor,
                        this.returnType);
            else
                return this.generateMethodSignature(this.methodName, this.methodDescriptor, this.returnType);
        }
    }
}