package compiler.codeGenerator;

import compiler.dag.DAGBracket;
import compiler.dag.DAGExpression;

public class BracketAccess extends MethodBodyContent {

    /**
     * 1. Load reference to the given array
     * 2. Push the array's index
     */
    private static String AALOAD = "??\n\tiaload";
    private DAGExpression arrayReference;
    private DAGExpression indexExpression;

    public BracketAccess(Function function, DAGBracket dagBracket) {
        super(function);
        this.arrayReference = dagBracket.getArrayExpression();
        this.indexExpression = dagBracket.getIndexExpression();
    }

    @Override
    public String toString() {

        Expression reference = new Expression(this.function, this.arrayReference);
        String referenceContent = reference.toString();

        Expression expression = new Expression(this.function, indexExpression);
        String expressionContent = expression.toString();

        return subst(AALOAD, referenceContent, expressionContent);
    }
}