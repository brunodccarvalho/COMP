package compiler.dag;

import compiler.symbols.FunctionLocals;
import static jjt.jmmTreeConstants.*;

public class DAGAssignment extends DAGNode {
    private DAGVariable assignVariable;
    private DAGExpression assignedExpression;

    public DAGAssignment(DAGVariable assignVariable, DAGExpression assignedExpression){
        this.assignVariable = assignVariable;
        this.assignedExpression = assignedExpression;

    }

    public DAGVariable getVariable(){
        return this.assignVariable;
    }

    public DAGExpression getExpression(){
        return this.assignedExpression;
    }
}