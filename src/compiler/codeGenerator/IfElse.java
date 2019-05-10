package compiler.codeGenerator;

import compiler.codeGenerator.utils.LabelGenerator;
import compiler.dag.BinaryOperator;
import compiler.dag.DAGBinaryOp;
import compiler.dag.DAGCondition;
import compiler.dag.DAGExpression;
import compiler.dag.DAGIfElse;
import compiler.dag.DAGNode;

public class IfElse extends Conditional {

    /**
     * 1. Condition
     * 2: If condition (IFEQ / IFLESS)
     * 2. Else label
     * 3. if body
     * 4. Goto label
     * 5. Else label
     * 6. Else body
     * 7. Goto label
     */
    private static String IFCOND = "?\n\t? ??\n\tgoto ?\n?:?\n?:";
    private static String IFEQ = "ifeq";
    private static String IFLESS = "if_icmplt";
    private LabelGenerator labelGenerator;
    private String cond;    // either IFCOND or IFEQ

    public IfElse(Function function, DAGIfElse branch, LabelGenerator labelGenerator) {
        super(function, branch);
        this.labelGenerator = labelGenerator;
        this.cond = IFEQ;
    }

    private String generateEQCondition(DAGExpression dagExpression) {
        Expression condition = new Expression(function, dagExpression);
        return condition.toString();
    }

    private String generateLESSCondition(DAGBinaryOp dagExpression) {
        DAGExpression lhsDag = ((DAGBinaryOp)dagExpression).getLhs();
        Expression lhsExpression = new Expression(function, lhsDag);
        DAGExpression rhsDag = ((DAGBinaryOp)dagExpression).getRhs();
        Expression rhsExpression = new Expression(function, rhsDag);
        return lhsExpression.toString() + rhsExpression.toString();
    }

    private String generateCondition(DAGExpression dagExpression) {
        if(dagExpression instanceof DAGBinaryOp) {
            BinaryOperator operator = ((DAGBinaryOp)dagExpression).getOperator();
            if(operator == BinaryOperator.LT) {
                this.cond = IfElse.IFLESS;
                return this.generateLESSCondition((DAGBinaryOp)dagExpression);
            }
        }
        return this.generateEQCondition(dagExpression);
    }

    @Override
    public String toString() {

        // Condition
        DAGCondition dagCondition = this.branch.getCondition();
        DAGExpression dagExpression = dagCondition.getExpression();
        String condition = this.generateCondition(dagExpression);
        
        // Else body and label
        String elseLabel = this.labelGenerator.nextLabel();
        DAGNode elseNode = ((DAGIfElse)this.branch).getElseNode();
        MethodBodyGenerator elseGenerator = new MethodBodyGenerator(this.function, elseNode);

        // If body and label
        String gotoLabel = this.labelGenerator.nextLabel();
        DAGNode thenNode = ((DAGIfElse)this.branch).getThenNode();
        MethodBodyGenerator thenGenerator = new MethodBodyGenerator(this.function, thenNode);


        return JVMInst.subst(IFCOND, condition, this.cond, elseLabel, thenGenerator.toString(), gotoLabel, elseLabel, elseGenerator.toString(), gotoLabel);
    }
}