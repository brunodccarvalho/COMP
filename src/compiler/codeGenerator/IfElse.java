package compiler.codeGenerator;

import compiler.dag.DAGBooleanConstant;
import compiler.dag.DAGCondition;
import compiler.dag.DAGExpression;
import compiler.dag.DAGIfElse;

public class IfElse extends Conditional {

    /**
     * 1. Condition
     * 2. End of if label
     * 3. If label
     * 4. End of if label
     */
    private static String IFEQ = "?\nifeq\ngoto ?\n?:\n?:";

    public IfElse(Function function, DAGIfElse branch) {
        super(function, branch);
    }

    @Override
    public String toString() {

        DAGCondition dagCondition = this.branch.getCondition();
        DAGExpression dagExpression = dagCondition.getExpression();

        if(dagExpression instanceof DAGBooleanConstant) {
            Expression expression = new Expression(function, dagExpression);
            System.out.println("-----> " + expression.toString());
        }
        return "";
    }
}