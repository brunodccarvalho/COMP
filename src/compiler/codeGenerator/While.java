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

    public While(Function function, DAGWhile branch) {
        super(function, branch, IFEQ);
    }

    @Override
    public String toString() {

        // Condition
        String conditionLabel = LabelGenerator.nextLabel();
        super.generateCondition();

        // While body
        DAGNode dagWhile = ((DAGWhile)branch).getBody();
        MethodBodyGenerator whileBody = new MethodBodyGenerator(function, dagWhile);

        // After while loop
        String continueLabel = LabelGenerator.nextLabel();

        return JVMInst.subst(WHILE, conditionLabel, this.condBody, this.cond, continueLabel, whileBody.toString(), conditionLabel, continueLabel);
    }
}
