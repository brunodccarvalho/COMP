package compiler.codeGenerator;

import compiler.dag.DAGCall;

/**
 * MethodCall
 */
public class MethodCall extends BaseByteCode {

    private Integer variableIndex;
    private MethodSignature methodSignature;
    private ParameterPush parameterPush;

    MethodCall(DAGCall methodCall) {
        this.variableIndex = CodeGenerator.singleton.variablesIndexes.get(methodCall.getCallClass().getName());
        this.methodSignature = new MethodSignature(methodCall);
        this.parameterPush = new ParameterPush(methodCall.getArguments());

    }

    @Override
    public String toString() {
        String methodCallBody = new String();
        if(variableIndex==null)
            variableIndex= new Integer(0);
        String invoke = this.subst(CodeGeneratorConstants.INVOKEVIRTUAL, Integer.toString(variableIndex),methodSignature.toString() + "\n");
        methodCallBody = methodCallBody.concat(parameterPush.toString()).concat(invoke);
        return methodCallBody;

    }

}