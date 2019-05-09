package compiler.codeGenerator;

import compiler.dag.DAGIntegerConstant;
/**
 * IntegerPush
 */
public class IntegerPush extends JVMInst {
    /**
     * 1: number to push to the stack - should be one of: 0,1,2,3,4,5 or m1 (-1)
     */
    public static String PUSHCONST = "\n\ticonst_?";
    /**
     * 1: number to push to the stack
     */
    public static String PUSHINT = "\n\tbipush ?";

    private int integerConstantValue;

    IntegerPush (DAGIntegerConstant integerConstant)
    {
        this.integerConstantValue = integerConstant.getValue();
    }

    @Override
    public String toString()
    {
        String integerPushBody = new String();
        if(integerConstantValue == -1) {
            integerPushBody = subst(IntegerPush.PUSHCONST, "m1");
        }
        else if((integerConstantValue >= 0) && (integerConstantValue <= 5)) {
            integerPushBody = subst(IntegerPush.PUSHCONST, String.valueOf(integerConstantValue));
        }
        else
            integerPushBody = subst(IntegerPush.PUSHINT, String.valueOf(integerConstantValue));
        return integerPushBody;

    }
}