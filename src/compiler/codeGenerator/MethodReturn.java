package compiler.codeGenerator;

import compiler.dag.DAGExpression;
import compiler.symbols.TypeDescriptor;

import java.util.HashMap;
/**
 * MethodReturn
 */
public class MethodReturn extends BaseByteCode {
    public static HashMap<String, String> returnTypes;
    static
    {
        returnTypes.put("int", "?\tireturn");
        returnTypes.put("boolean", "?\tireturn");
        returnTypes.put("int[]", "?\tareturn");
        returnTypes.put("void", "?\treturn");
    }

    private TypeDescriptor returnType;
    private Expression expression;
    private Method belongs;

    MethodReturn(Method belongs,DAGExpression returnExpression)
    {
        this.belongs=belongs;
        this.returnType= returnExpression.getType();
        this.expression= new Expression(returnExpression);


    }

    @Override
    public String toString()
    {
        String type=returnType.getName();
        String returnRegex=MethodReturn.returnTypes.get(type);
        if(returnRegex==null)
            returnRegex="?\n\tareturn";
        return subst(returnRegex,this.expression.toString());
    }
    
}