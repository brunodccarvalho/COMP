package compiler.codeGenerator;

import compiler.dag.BinaryOperator;
import compiler.dag.DAGBinaryOp;
import compiler.dag.DAGBooleanConstant;
import compiler.dag.DAGBracket;
import compiler.dag.DAGBracketAssignment;
import compiler.dag.DAGExpression;
import compiler.dag.DAGIntegerConstant;
import compiler.dag.DAGLocal;
import compiler.dag.DAGMember;
import compiler.dag.DAGMethodCall;
import compiler.dag.DAGNew;
import compiler.dag.DAGNewIntArray;
import compiler.dag.DAGParameter;
import compiler.dag.DAGVariable;

public class Expression extends BaseStatement {

    private DAGExpression expression;

    Expression(Function function, DAGExpression expression)
    {
        super(function);
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
        else if(expression instanceof DAGVariable) {
            Variable variable = new Variable(this.function, (DAGVariable)expression);
            expressionBody = expressionBody.concat(variable.toString());
        }
        else if(expression instanceof DAGIntegerConstant) {
            IntegerPush integerLoadBody = new IntegerPush((DAGIntegerConstant)expression);
            expressionBody = expressionBody.concat(integerLoadBody.toString());
        }
        else if(expression instanceof DAGMethodCall) {
            MethodCall callBody = new MethodCall(this.function, (DAGMethodCall)expression);
            expressionBody = expressionBody.concat(callBody.toString());
        }
        else if(expression instanceof DAGBooleanConstant) {
            BooleanConstant booleanConstant = new BooleanConstant((DAGBooleanConstant)expression);
            expressionBody = expressionBody.concat(booleanConstant.toString());
        }
        else if(expression instanceof DAGBracket) {
            BracketAccess bracketAccess = new BracketAccess(this.function, (DAGBracket)expression);
            expressionBody = expressionBody.concat(bracketAccess.toString());
        }
        else if(expression instanceof DAGNewIntArray) {
            NewIntArray newObject = new NewIntArray(this.function, (DAGNewIntArray)expression);
            expressionBody = expressionBody.concat(newObject.toString());
        }

        // TODO: missing some instances of expression?
        return expressionBody;
    }
    
}