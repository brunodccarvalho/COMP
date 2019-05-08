package compiler.codeGenerator;

import compiler.dag.BinaryOperator;
import compiler.dag.DAGBinaryOp;
import compiler.dag.DAGCall;
import compiler.dag.DAGExpression;
import compiler.dag.DAGIntegerConstant;
import compiler.dag.DAGLocal;
import compiler.dag.DAGMember;
import compiler.dag.DAGParameter;
/**
 * Expression
 */
public class Expression extends BaseStatement{

    private DAGExpression expression;

    Expression(DAGExpression expression)
    {
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
            Expression lhsBody = new Expression(lhs);
            Expression rhsBody = new Expression(rhs);
            Operator operatorBody = new Operator(operator);
            expressionBody = expressionBody.concat(lhsBody.toString()).concat(rhsBody.toString()).concat(operatorBody.toString());
        }
        else if(expression instanceof DAGMember)
        {
            MemberLoad member = new MemberLoad((DAGMember)expression);
            expressionBody = expressionBody.concat(member.toString());
        }
        else if(expression instanceof DAGLocal) {
            Load loadBody = new Load((DAGLocal)expression);
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
        else if(expression instanceof DAGCall) {
            MethodCall callBody = new MethodCall((DAGCall)expression);
            expressionBody = expressionBody.concat(callBody.toString());
        }
        return expressionBody;
        
    }
    
}