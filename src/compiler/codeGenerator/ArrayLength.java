package compiler.codeGenerator;

import compiler.dag.DAGExpression;
import compiler.dag.DAGLength;

public class ArrayLength extends MethodBodyContent {

    /**
     * 1. Expression corresponding to an array
     */
    private static String ARRAYLENGTH = "?\n\tarraylength";
    private DAGExpression expression;

    public ArrayLength(Function function, DAGLength dagLength) {
        super(function);
        this.expression = dagLength.getExpression();
    }

    @Override
    public String toString() {
        Expression lengthExpression = new Expression(this.function, this.expression);
        String lengthExpressionContent = lengthExpression.toString();
        return subst(ARRAYLENGTH, lengthExpressionContent);
    }
}