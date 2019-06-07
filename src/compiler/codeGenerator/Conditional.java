package compiler.codeGenerator;

import compiler.dag.BinaryOperator;
import compiler.dag.DAGBinaryOp;
import compiler.dag.DAGBranch;
import compiler.dag.DAGCondition;
import compiler.dag.DAGExpression;

public abstract class Conditional extends BaseStatement {

    protected static String IFEQ = "\tifeq";
    protected static String IFGE = "\tif_icmpge";
    protected DAGBranch branch;
    protected String cond;  // either IFEQ or IFGE
    protected String condBody;

    public Conditional(Function function, DAGBranch branch, String cond) {
        super(function);
        this.branch = branch;
        this.cond = cond;
    }

    protected String generateEQCondition(DAGExpression dagExpression) {
        Expression condition = new Expression(function, dagExpression);
        return condition.toString();
    }

    protected String generateLESSCondition(DAGBinaryOp dagExpression) {
        DAGExpression lhsDag = ((DAGBinaryOp)dagExpression).getLhs();
        Expression lhsExpression = new Expression(function, lhsDag);
        DAGExpression rhsDag = ((DAGBinaryOp)dagExpression).getRhs();
        Expression rhsExpression = new Expression(function, rhsDag);
        return lhsExpression.toString() + rhsExpression.toString();
    }

    protected void generateCondition() {
        DAGCondition dagCondition = this.branch.getCondition();
        DAGExpression dagExpression = dagCondition.getExpression();
        if(dagExpression instanceof DAGBinaryOp) {
            BinaryOperator operator = ((DAGBinaryOp)dagExpression).getOperator();
            if(operator == BinaryOperator.LT) {
                this.cond = IfElse.IFGE;
                this.condBody = this.generateLESSCondition((DAGBinaryOp)dagExpression);
                return;
            }
        }
        this.condBody = this.generateEQCondition(dagExpression);
    }
}
