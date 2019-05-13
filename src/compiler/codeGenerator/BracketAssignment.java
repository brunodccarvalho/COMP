package compiler.codeGenerator;

import compiler.dag.DAGBracketAssignment;
import compiler.dag.DAGExpression;

public class BracketAssignment extends Assignment {

    /**
     * 1. Load reference to the given array
     * 2. Push the array's index
     * 3. Load value to be assigned to the a position of the array
     */
    private static String AASTORE = "???\n\taastore";
    private DAGExpression indexExpression;

    public BracketAssignment(Function function, DAGBracketAssignment statement) {
        super(function, statement);
        this.indexExpression = statement.getIndexExpression();
    }

    @Override
    public String toString() {
        
        Variable bracketAccess = new Variable(this.function, this.variable);
        String referenceLoad = bracketAccess.toString();

        Expression expression = new Expression(this.function, indexExpression);
        String expressionContent = expression.toString();

        return subst(AASTORE, referenceLoad, expressionContent, expressionBody.toString());
    }
}