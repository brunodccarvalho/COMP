package compiler.codeGenerator;

import compiler.dag.DAGExpression;
import compiler.modules.CodeGenerator;
import compiler.symbols.TypeDescriptor;

import java.util.HashMap;

/**
 * MethodReturn
 */
public class MethodReturn extends JVMInst {

    private CodeGenerator codeGenerator;
    public static HashMap<String, String> returnTypes;
    static {
        returnTypes = new HashMap<>();
        returnTypes.put("int", "?\tireturn");
        returnTypes.put("boolean", "?\tireturn");
        returnTypes.put("int[]", "?\tareturn");
        returnTypes.put("void", "?\treturn");
    }

    private TypeDescriptor returnType;
    private Expression expression;
    private Method belongs;

    /*MethodReturn(CodeGenerator codeGenerator, Method belongs, DAGExpression returnExpression) {
        this.codeGenerator = codeGenerator;
        this.belongs = belongs;
        this.returnType = returnExpression.getType();
        this.expression = new Expression(this.codeGenerator, returnExpression);

    }*/

    @Override
    public String toString() {
        String type = returnType.getName();
        String returnRegex = MethodReturn.returnTypes.get(type);
        if (returnRegex == null)
            returnRegex = "?\n\tareturn";
        return subst(returnRegex, this.expression.toString());
    }

}