package compiler.codeGenerator;

import compiler.dag.DAGExpression;
import compiler.dag.DAGMethodCall;
import compiler.dag.DAGVariable;
import compiler.symbols.TypeDescriptor;

public class MethodCallSpecial extends MethodBodyContent {

    private static final String INVOKEVIRTUAL = "\n\taload ??\n\tinvokevirtual ? ";
    private static final String INVOKEVIRTUALPOP = "\n\taload ??\n\tinvokevirtual ? \n\tpop";
    private MethodSignature methodSignature;
    private Integer callObjectIndex;
    private ParameterPush parameterPush;
    private TypeDescriptor returnType;

    MethodCallSpecial(Function function, DAGMethodCall methodCall) {
        super(function);
        this.returnType = methodCall.getType();
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
        String invoke = new String();
        if(this.returnType.toString().equals("void"))
            invoke = JVMInst.subst(MethodCallSpecial.INVOKEVIRTUAL, Integer.toString(callObjectIndex),parameterPush.toString(), methodSignature.toString());
        else
            invoke = JVMInst.subst(MethodCallSpecial.INVOKEVIRTUALPOP, Integer.toString(callObjectIndex),parameterPush.toString(), methodSignature.toString());

        methodCallBody = methodCallBody.concat(invoke);
        return methodCallBody;
    }
}