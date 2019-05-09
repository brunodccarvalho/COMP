package compiler.codeGenerator;

import compiler.dag.DAGExpression;

import java.util.ArrayList;
/**
 * ParameterPush
 */
public class ParameterPush {

    ArrayList<Expression> expressions;

    ParameterPush(DAGExpression[] parameters)
    {
        this.expressions= new ArrayList<Expression>();
        for(DAGExpression parameter: parameters) {
            Expression parameterExpression = new Expression(parameter);
            expressions.add(parameterExpression);
        }
    }

    @Override
    public String toString()
    {
        String parameterPush = new String();
        for(Expression expression: expressions) {
            parameterPush = parameterPush.concat(expression.toString());
        }
        return parameterPush;

    }
    
}