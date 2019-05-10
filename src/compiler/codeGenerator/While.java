package compiler.codeGenerator;

import compiler.codeGenerator.utils.LabelGenerator;
import compiler.dag.DAGNode;
import compiler.dag.DAGWhile;

public class While extends Conditional {

    /**
     * 1. Condition label
     * 2. Condition body
     * 3. Condition (either IFEQ oR IFGE)
     * 4. Continue label
     * 5. While body
     * 6. Condition label
     * 7. Continue label
     */
    private static String WHILE = "\n?:?\n? ??\n\tgoto ?\n?:\n";

    public While(Function function, DAGWhile branch, LabelGenerator labelGenerator) {
        super(function, branch, labelGenerator, IFEQ);
    }

    @Override
    public String toString() {

        // Condition
        String conditionLabel = this.labelGenerator.nextLabel();
        super.generateCondition();

        // While body
        DAGNode dagWhile = ((DAGWhile)branch).getBody();
        MethodBodyGenerator whileBody = new MethodBodyGenerator(function, dagWhile);

        // After while loop
        String continueLabel = this.labelGenerator.nextLabel();
        
        return JVMInst.subst(WHILE, conditionLabel, this.condBody, this.cond, continueLabel, whileBody.toString(), conditionLabel, continueLabel);
    }
}