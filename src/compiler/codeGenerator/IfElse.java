package compiler.codeGenerator;

import compiler.codeGenerator.utils.LabelGenerator;
import compiler.dag.DAGIfElse;
import compiler.dag.DAGNode;

public class IfElse extends Conditional {

    /**
     * 1. Condition
     * 2: If condition (IFEQ / IFGE)
     * 2. Else label
     * 3. if body
     * 4. Goto label
     * 5. Else label
     * 6. Else body
     * 7. Goto label
     */
    private static String IFCOND = "?\n\t? ??\n\tgoto ?\n?:?\n?:";

    public IfElse(Function function, DAGIfElse branch, LabelGenerator labelGenerator) {
        super(function, branch, labelGenerator, IFEQ);
    }

    @Override
    public String toString() {

        // Condition
        super.generateCondition();
        
        // Else body and label
        String elseLabel = this.labelGenerator.nextLabel();
        DAGNode elseNode = ((DAGIfElse)this.branch).getElseNode();
        MethodBodyGenerator elseGenerator = new MethodBodyGenerator(this.function, elseNode);

        // If body and label
        String gotoLabel = this.labelGenerator.nextLabel();
        DAGNode thenNode = ((DAGIfElse)this.branch).getThenNode();
        MethodBodyGenerator thenGenerator = new MethodBodyGenerator(this.function, thenNode);

        return JVMInst.subst(IFCOND, this.condBody, this.cond, elseLabel, thenGenerator.toString(), gotoLabel, elseLabel, elseGenerator.toString(), gotoLabel);
    }
}