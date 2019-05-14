package compiler.codeGenerator;

import compiler.dag.DAGAssignment;
import compiler.dag.DAGBracketAssignment;
import compiler.dag.DAGExpression;
import compiler.dag.DAGMember;
import compiler.dag.DAGVariable;

public class Assignment extends BaseStatement {

    protected DAGVariable variable;
    protected DAGExpression expression;
    protected Expression expressionBody;
    protected DAGAssignment statement;

    Assignment(Function function, DAGAssignment statement){
        super(function);
        this.statement = statement;
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
            Store store = new Store(this.function, (DAGVariable)variable);
            variableStore = store.toString();
        }

        assignmentBody = assignmentBody.concat(expressionBody.toString()).concat(variableStore);
        return assignmentBody;
    }
}
