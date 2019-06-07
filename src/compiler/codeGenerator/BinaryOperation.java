package compiler.codeGenerator;

import compiler.codeGenerator.utils.LabelGenerator;
import compiler.dag.BinaryOperator;
import compiler.dag.DAGBinaryOp;
import compiler.dag.DAGExpression;
import compiler.exceptions.InternalCompilerError;

public class BinaryOperation extends MethodBodyContent {

    private final DAGBinaryOp expression;

    /**
     * SUM, SUB, MUL, DIV
     *
     *    $lhs...        [\n\t]?
     *    $rhs...        [\n\t]?
     *    op             \n\tOP
     */
    private static String SUM = "??\n\tiadd";
    private static String SUB = "??\n\tisub";
    private static String MUL = "??\n\timul";
    private static String DIV = "??\n\tidiv";

    /**
     * AND:
     *
     *    $lhs...         [\n\t]?                1 lhs
     *    ifeq A          \n\tifeq ?             2 label A
     *    $rhs...         [\n\t]?                3 rhs
     *    ifeq A          \n\tifeq ?             4 label A
     *    iconst_1        \n\ticonst_1
     *    goto B          \n\tgoto ?             5 label B
     * A: iconst_0        \n?:\n\ticonst_0       6 label A
     * B: ...             \n?:                   7 label B
     */
    private static String AND = "?\n\tifeq ??\n\tifeq ?\n\ticonst_1\n\tgoto ?\n?:\n\ticonst_0\n?:";

    /**
     * LESS:
     *
     *    $lhs...         [\n\t]?                1 lhs
     *    $rhs...         [\n\t]?                2 rhs
     *    if_icmpge A     \n\tif_icmpge ?        3 label A
     *    iconst_1        \n\ticonst_1
     *    goto B          \n\tgoto ?             4 label B
     * A: iconst_0        \n?: iconst_0          5 label A
     * B: ...             \n?:                   6 label B
     */
    private static String LT = "??\n\tif_icmpge ?\n\ticonst_1\n\tgoto ?\n?:\n\ticonst_0\n?:";

    BinaryOperation(Function function, DAGBinaryOp expression)
    {
        super(function);
        this.expression = expression;
    }

    @Override
    public String toString()
    {
        DAGExpression lhs = expression.getLhs(), rhs = expression.getRhs();
        BinaryOperator op = expression.getOperator();
        String lhsStr = new Expression(this.function, lhs).toString();
        String rhsStr = new Expression(this.function, rhs).toString();

        switch(op){
            case SUM:
                return subst(SUM, lhsStr, rhsStr);
            case SUB:
                return subst(SUB, lhsStr, rhsStr);
            case MUL:
                return subst(MUL, lhsStr, rhsStr);
            case DIV:
                return subst(DIV, lhsStr, rhsStr);
            case AND: {
                String A = LabelGenerator.nextLabel();
                String B = LabelGenerator.nextLabel();
                return subst(AND, lhsStr, A, rhsStr, A, B, A, B);
            }
            case LT: {
                String A = LabelGenerator.nextLabel();
                String B = LabelGenerator.nextLabel();
                return subst(LT, lhsStr, rhsStr, A, B, A, B);
            }
        }

        throw new InternalCompilerError();
    }

}
