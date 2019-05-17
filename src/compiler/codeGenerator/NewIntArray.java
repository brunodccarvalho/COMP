package compiler.codeGenerator;

import compiler.dag.DAGExpression;
import compiler.dag.DAGNewIntArray;

public class NewIntArray extends NewObject {

    /**
     * 1. Expression which represents the number of elements in the array
     */
    private static String NEWINTARRAY = "?\n\tnewarray int";

    public NewIntArray(Function function, DAGNewIntArray dagNew) {
        super(function, dagNew);
    }

    @Override
    public String toString() {
        DAGExpression countExpression = ((DAGNewIntArray)dagNew).getIndexExpression();
        Expression countExpressionContent = new Expression(this.function, countExpression);
        return subst(NEWINTARRAY, countExpressionContent.toString());
    }
}