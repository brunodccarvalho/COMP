package compiler.codeGenerator;

import compiler.codeGenerator.utils.LabelGenerator;
import compiler.dag.DAGIfElse;
import compiler.dag.DAGNode;

public class IfElse extends Conditional {

    /**
     * 1. Condition
     * 2: If condition (IFEQ / IFGE)
     * 2. If label
     * 3. Else body
     * 4. Goto label
     * 5. If label
     * 6. if body
     * 7. Goto label
     */
    private static String IFCOND = "?\n? ??\n\tgoto ?\n?:?\n?:";

    public IfElse(Function function, DAGIfElse branch, LabelGenerator labelGenerator) {
        super(function, branch, labelGenerator, IFEQ);
    }

    @Override
    public String toString() {

        // Condition
        super.generateCondition();
        
        // If body and label
        String ifLabel = this.labelGenerator.nextLabel();
        DAGNode ifNode = ((DAGIfElse)this.branch).getThenNode();
        MethodBodyGenerator thenGenerator = new MethodBodyGenerator(this.function, ifNode);

        // Else body and label
        String gotoLabel = this.labelGenerator.nextLabel();
        DAGNode elseNode = ((DAGIfElse)this.branch).getElseNode();
        MethodBodyGenerator elseGenerator = new MethodBodyGenerator(this.function, elseNode);

        return JVMInst.subst(IFCOND, this.condBody, this.cond, ifLabel, elseGenerator.toString(), gotoLabel, ifLabel, thenGenerator.toString(), gotoLabel);
    }
}