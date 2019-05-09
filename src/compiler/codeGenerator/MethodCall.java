package compiler.codeGenerator;

import compiler.dag.DAGExpression;
import compiler.dag.DAGMethodCall;
import compiler.dag.DAGVariable;

public class MethodCall extends MethodBodyContent {

    private static final String INVOKEVIRTUAL = "\n\taload ?\n\tinvokevirtual ? ";
    private MethodSignature methodSignature;
    private Integer callObjectIndex;
    private ParameterPush parameterPush;

    MethodCall(Function function, DAGMethodCall methodCall) {
        super(function);
        DAGExpression expression = (((DAGMethodCall)methodCall).getObjectExpression());
        if(expression instanceof DAGVariable)
            this.callObjectIndex = this.function.variablesIndexes.get(((DAGVariable)expression).getVariable());
        this.methodSignature = new MethodSignature(methodCall);
        this.parameterPush = new ParameterPush(this.function, methodCall.getArguments());
    }

    @Override
    public String toString() {
        if(this.callObjectIndex == null)
            this.callObjectIndex = 0;
        String methodCallBody = new String();
        String invoke = JVMInst.subst(MethodCall.INVOKEVIRTUAL, Integer.toString(callObjectIndex), methodSignature.toString());
        methodCallBody = methodCallBody.concat(parameterPush.toString()).concat(invoke);
        return methodCallBody;
    }
}