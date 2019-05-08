package compiler.codeGenerator;

import compiler.dag.DAGAssignment;
import compiler.dag.DAGExpression;
import compiler.dag.DAGMember;
import compiler.dag.DAGVariable;
/**
 * Assignment
 */
public class Assignment extends BaseStatement{

    private DAGExpression variable;
    private DAGExpression expression;
    private Expression expressionBody;

    Assignment(DAGAssignment statement){
        this.variable = statement.getVariable();
        this.expression = statement.getExpression();
        this.expressionBody = new Expression(expression);
    }    

    @Override
    public String toString()
    {
        String assignmentBody = new String();
        String variableStore= new String();
        if(this.variable instanceof DAGMember)
        {
            StoreMember storeMember =  new StoreMember((DAGMember)variable);
            variableStore = storeMember.toString();
        }
        else 
        {
            Store store= new Store((DAGVariable)variable);
            variableStore = store.toString();
        }
        assignmentBody = assignmentBody.concat(expressionBody.toString()).concat(variableStore);
        return assignmentBody;
    }
}