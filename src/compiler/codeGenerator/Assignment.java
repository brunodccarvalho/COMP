package compiler.codeGenerator;

import compiler.dag.DAGAssignment;
import compiler.dag.DAGExpression;
import compiler.dag.DAGMember;
import compiler.dag.DAGVariable;

public class Assignment extends BaseStatement{

    private Function function;
    private DAGExpression variable;
    private DAGExpression expression;
    private Expression expressionBody;

    Assignment(Function function, DAGAssignment statement){
        this.function = function;
        this.variable = statement.getVariable();
        this.expression = statement.getExpression();
        this.expressionBody = new Expression(this.function, expression);
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
            Store store= new Store(this.function, (DAGVariable)variable);
            variableStore = store.toString();
        }
        assignmentBody = assignmentBody.concat(expressionBody.toString()).concat(variableStore);
        return assignmentBody;
    }
}