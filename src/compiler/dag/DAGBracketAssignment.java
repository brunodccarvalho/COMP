package compiler.dag;


public class DAGBracketAssignment extends DAGAssignment {
    private DAGExpression indexExpression;
    
    public DAGBracketAssignment(DAGVariable assignVariable, DAGExpression assignedExpression, DAGExpression indexExpression){
        super(assignVariable,assignedExpression);
        this.indexExpression = indexExpression;
    }

    public DAGExpression getIndexExpression(){
        return this.indexExpression;
    }
}