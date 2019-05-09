package compiler.codeGenerator;

import compiler.dag.BinaryOperator;
import compiler.dag.DAGBinaryOp;
import compiler.dag.DAGExpression;
import compiler.dag.DAGIntegerConstant;
import compiler.dag.DAGLocal;
import compiler.dag.DAGMember;
import compiler.dag.DAGMethodCall;
import compiler.dag.DAGParameter;

public class Expression extends BaseStatement {

    private Function function;
    private DAGExpression expression;

    Expression(Function function, DAGExpression expression)
    {
        this.function = function;
        this.expression=expression;
    }

    @Override
    public String toString()
    {
        String expressionBody = new String();
        if(expression instanceof DAGBinaryOp) {
            DAGExpression lhs = ((DAGBinaryOp)expression).getLhs();
            DAGExpression rhs = ((DAGBinaryOp)expression).getRhs();
            BinaryOperator operator = ((DAGBinaryOp)expression).getOperator();
            Expression lhsBody = new Expression(this.function, lhs);
            Expression rhsBody = new Expression(this.function, rhs);
            Operator operatorBody = new Operator(operator);
            expressionBody = expressionBody.concat(lhsBody.toString()).concat(rhsBody.toString()).concat(operatorBody.toString());
        }
        else if(expression instanceof DAGMember)
        {
            MemberLoad member = new MemberLoad((DAGMember)expression);
            expressionBody = expressionBody.concat(member.toString());
        }
        else if(expression instanceof DAGLocal) {
            Load loadBody = new Load(this.function, (DAGLocal)expression);
            expressionBody = expressionBody.concat(loadBody.toString());
        }
        else if(expression instanceof DAGParameter)
        {
            LoadParameter member = new LoadParameter((DAGParameter)expression);
            expressionBody = expressionBody.concat(member.toString());
        }
        else if(expression instanceof DAGIntegerConstant) {
            IntegerPush integerLoadBody = new IntegerPush((DAGIntegerConstant)expression);
            expressionBody = expressionBody.concat(integerLoadBody.toString());
        }
        else if(expression instanceof DAGMethodCall) {
            MethodCall callBody = new MethodCall(this.function, (DAGMethodCall)expression);
            expressionBody = expressionBody.concat(callBody.toString());
        }
        return expressionBody;
        
    }
    
}