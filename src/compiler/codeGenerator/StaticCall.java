package compiler.codeGenerator;

import compiler.dag.DAGExpression;
import compiler.dag.DAGStaticCall;
import compiler.symbols.TypeDescriptor;

import java.util.ArrayList;

/**
 * StaticCall
 */
public class StaticCall extends MethodBodyContent {

    private static String STATICCALL = "\tinvokestatic ?/?(?)?";

    private ArrayList<Expression> exps;
    private String className;
    private String methodName;
    private TypeDescriptor returnType;
    private String parametersTypes;

    StaticCall(Function function, DAGStaticCall call)
    {
        super(function);
        this.exps= new ArrayList<Expression>();
        this.className= call.getCallClass().toString();
        this.methodName = call.getMethodName();
        this.returnType = call.getCallable().getReturnType();
        this.parametersTypes="";
        for(DAGExpression ex : call.getArguments())
        {
            this.exps.add(new Expression(function, ex));
            this.parametersTypes += CodeGeneratorConstants.getType(ex.getType());
        }
    }

    @Override
    public String toString()
    {
        String devolver= new String();
        for(Expression exp:this.exps)
        {
            devolver += exp.toString();
        }
        String invoke = subst(StaticCall.STATICCALL, this.className,this.methodName,this.parametersTypes,CodeGeneratorConstants.getType(this.returnType));
        return devolver + "\n" + invoke;
    }
    
}