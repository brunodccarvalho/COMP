package compiler.codeGenerator;

import compiler.dag.BinaryOperator;

import java.util.HashMap;
/**
 * Operator
 */
public class Operator extends BaseByteCode {
    
    public static HashMap<String, String> binaryOperators;
    static{
        binaryOperators.put("+", "\tiadd");
        binaryOperators.put("-", "\tisub");
        binaryOperators.put("*", "\timul");
        binaryOperators.put("/", "\tidiv");
        binaryOperators.put("<", "\tdcmpg");
        binaryOperators.put("&&", "\tiand");
    }

    private BinaryOperator operator;

    Operator(BinaryOperator operator)
    {
        this.operator=operator;
    }

    @Override
    public String toString()
    {
        return Operator.binaryOperators.get(operator.toString()) + "\n";

    }
    
}