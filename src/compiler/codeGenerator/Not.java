package compiler.codeGenerator;

import compiler.dag.DAGExpression;
import compiler.dag.DAGNot;

public class Not extends MethodBodyContent {
    /**
     * 1. Expression being negated
     */
    private static String NOT = "\n\ticonst_1?\n\tixor";
    private DAGExpression expression;

    public Not(Function function, DAGNot dagNot) {
        super(function);
        this.expression = dagNot.getExpression();
    }

    @Override
    public String toString() {
        Expression notExpression = new Expression(this.function, this.expression);
        String notExpressionContent = notExpression.toString();
        return subst(NOT, notExpressionContent);
    }
}