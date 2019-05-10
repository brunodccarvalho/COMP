package compiler.codeGenerator;

import compiler.dag.DAGBooleanConstant;

public class BooleanConstant extends JVMInst {

    private static String pushTrue = "\n\ticonst_1";
    private static String pushFalse = "\n\ticonst_0";
    private boolean booleanValue;

    public BooleanConstant(DAGBooleanConstant dagBooleanConstant) {
        this.booleanValue = dagBooleanConstant.getValue();
    }

    @Override
    public String toString()
    {
        if(booleanValue)
            return pushTrue;
        else
            return pushFalse;
    }
}