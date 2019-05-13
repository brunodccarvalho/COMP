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
     * 1: number to push to the stack (-128, ... , 127)
     */
    public static String BIPUSH = "\n\tbipush ?";
    /**
     * 1: number to push to the stack (-32768, ... , 32767)
     */
    public static String SIPUSH = "\n\tsipush ?";
    /**
     * 1: number to push to the stack (other than the previously mentioned ones)
     */
    public static String LDC = "\n\tldc ?";

    private int integerConstantValue;

    IntegerPush (DAGIntegerConstant integerConstant)
    {
        this.integerConstantValue = integerConstant.getValue();
    }

    @Override
    public String toString()
    {
        String integerPushBody = new String();
        if(integerConstantValue == -1)
            integerPushBody = subst(IntegerPush.PUSHCONST, "m1");
        else if((integerConstantValue >= 0) && (integerConstantValue <= 5))
            integerPushBody = subst(IntegerPush.PUSHCONST, String.valueOf(integerConstantValue));
        else {
            String inst;
            if(((integerConstantValue >= -128) && (integerConstantValue <= 127)))
                inst = IntegerPush.BIPUSH;
            else if(((integerConstantValue >= -32768) && (integerConstantValue <= 32767)))
                inst = IntegerPush.SIPUSH;
            else
                inst = IntegerPush.LDC;
            integerPushBody = subst(inst, String.valueOf(integerConstantValue));
        }
        return integerPushBody;
    }
}