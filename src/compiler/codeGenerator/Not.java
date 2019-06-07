package compiler.codeGenerator;

import compiler.codeGenerator.utils.LabelGenerator;
import compiler.dag.DAGExpression;
import compiler.dag.DAGNot;

public class Not extends MethodBodyContent {
    /**
     *    $bool...      [\n\t]?                1 bool
     *    ifne A        \n\tifne ?             2 label A
     *    iconst_1      \n\ticonst_1
     *    goto B        \n\tgoto ?             3 label B
     * A: iconst_0      \n?:\n\ticonst_0       4 label A
     * B: ...           \n?:                   5 label B
     */
    private static String NOT = "?\n\tifne ?\n\ticonst_1\n\tgoto ?\n?:\n\ticonst_0\n?:";
    private DAGExpression expression;

    public Not(Function function, DAGNot dagNot) {
        super(function);
        this.expression = dagNot.getExpression();
    }

    @Override
    public String toString() {
        String exprStr = new Expression(this.function, expression).toString();
        String A = LabelGenerator.nextLabel();
        String B = LabelGenerator.nextLabel();
        return subst(NOT, exprStr, A, B, A, B);
    }
}
