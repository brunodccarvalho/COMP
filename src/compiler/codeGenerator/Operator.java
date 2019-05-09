package compiler.codeGenerator;

import compiler.dag.BinaryOperator;

import java.util.HashMap;
/**
 * Operator
 */
public class Operator {
    
    public static HashMap<String, String> binaryOperators;
    static{
        binaryOperators = new HashMap<>();
        binaryOperators.put("+", "\n\tiadd");
        binaryOperators.put("-", "\n\tisub");
        binaryOperators.put("*", "\n\timul");
        binaryOperators.put("/", "\n\tidiv");
        binaryOperators.put("<", "\n\tdcmpg");
        binaryOperators.put("&&", "\n\tiand");
    }

    private BinaryOperator operator;

    Operator(BinaryOperator operator)
    {
        this.operator=operator;
    }

    @Override
    public String toString()
    {
        return Operator.binaryOperators.get(operator.toString());

    }
    
}