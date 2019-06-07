package compiler.codeGenerator;

import compiler.dag.DAGExpression;
import compiler.dag.DAGReturn;
import compiler.dag.DAGReturnExpression;
import compiler.dag.DAGVoidReturn;
import compiler.modules.CodeGenerator;
import compiler.symbols.TypeDescriptor;

import java.util.HashMap;

/**
 * MethodReturn
 */
public class MethodReturn extends BaseStatement {

    public static HashMap<String, String> returnTypes;
    static {
        returnTypes = new HashMap<>();
        returnTypes.put("int", "?\n\tireturn");
        returnTypes.put("boolean", "?\n\tireturn");
        returnTypes.put("int[]", "?\n\tareturn");
        returnTypes.put("void", "\n\treturn");
    }

    private TypeDescriptor returnType;
    private Expression expression;

    MethodReturn(Function function, DAGReturn returnExpression) {
        super(function);
        this.returnType = returnExpression.getType();
        if(returnExpression instanceof DAGVoidReturn)
            this.expression = null;
        else if(returnExpression instanceof DAGReturnExpression)
            this.expression = new Expression(function, ((DAGReturnExpression)returnExpression).getExpression());
    }

    @Override
    public String toString() {
        String type = returnType.getName();
        String returnRegex = MethodReturn.returnTypes.get(type);
        if (returnRegex == null)
            returnRegex = "?\n\tareturn";
        if(this.expression==null)
            return returnRegex;
        else
            return subst(returnRegex, this.expression.toString());
    }

}