package compiler.codeGenerator;

import compiler.dag.DAGExpression;
import java.util.ArrayList;

public class ParameterPush extends MethodBodyContent {

    private ArrayList<Expression> expressions;

    ParameterPush(Function function, DAGExpression[] parameters)
    {
        super(function);
        this.expressions= new ArrayList<Expression>();
        for(DAGExpression parameter: parameters) {
            Expression parameterExpression = new Expression(this.function, parameter);
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