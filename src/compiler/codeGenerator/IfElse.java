package compiler.codeGenerator;

import compiler.codeGenerator.utils.LabelGenerator;
import compiler.dag.DAGBooleanConstant;
import compiler.dag.DAGCondition;
import compiler.dag.DAGExpression;
import compiler.dag.DAGIfElse;
import compiler.dag.DAGNode;

public class IfElse extends Conditional {

    /**
     * 1. Condition
     * 2. If label
     * 3. Else body
     * 4. Goto label
     * 5. If label
     * 6. If body
     * 7. Goto label
     */
    private static String IFEQ = "?\n\tifeq ??\n\tgoto ?\n?:?\n?:";
    private LabelGenerator labelGenerator;

    public IfElse(Function function, DAGIfElse branch, LabelGenerator labelGenerator) {
        super(function, branch);
        this.labelGenerator = labelGenerator;
    }

    @Override
    public String toString() {

        // Condition
        DAGCondition dagCondition = this.branch.getCondition();
        DAGExpression dagExpression = dagCondition.getExpression();
        Expression condition = new Expression(function, dagExpression);

        // If body and label
        String ifLabel = this.labelGenerator.nextLabel();
        DAGNode thenNode = ((DAGIfElse)this.branch).getThenNode();
        MethodBodyGenerator thenGenerator = new MethodBodyGenerator(this.function, thenNode);

        // Else body and label
        String gotoLabel = this.labelGenerator.nextLabel();
        DAGNode elseNode = ((DAGIfElse)this.branch).getElseNode();
        MethodBodyGenerator elseGenerator = new MethodBodyGenerator(this.function, elseNode);

        return JVMInst.subst(IFEQ, condition.toString(), ifLabel, elseGenerator.toString(), gotoLabel, ifLabel, thenGenerator.toString(), gotoLabel);
    }
}