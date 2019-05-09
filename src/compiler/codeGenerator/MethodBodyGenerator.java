package compiler.codeGenerator;

import compiler.dag.DAGAssignment;
import compiler.dag.DAGExpression;
import compiler.dag.DAGMulti;
import compiler.dag.DAGNode;
import compiler.dag.DAGBranch;
import compiler.symbols.JMMFunction;

import java.util.ArrayList;

public class MethodBodyGenerator {

    private Function function;
    private ArrayList<BaseStatement> statements;

    MethodBodyGenerator(Function function, JMMFunction methodDescriptor)
    {
        this.function = function;
        this.statements = new ArrayList<BaseStatement>();
        DAGMulti multiNodes = function.data.bodiesMap.get(methodDescriptor);
        DAGNode[] nodes = multiNodes.getNodes();
        for (DAGNode statement : nodes) {
            if(statement instanceof DAGAssignment) {
                Assignment assignment = new Assignment(this.function, (DAGAssignment)statement);
                this.statements.add(assignment);
            }
            else if(statement instanceof DAGExpression) {
                Expression expression = new Expression(this.function, (DAGExpression)statement);
                this.statements.add(expression);
            }
            else if(statement instanceof DAGBranch) {
                System.out.println("this is a branch!");
            }
        }
    }

    @Override
    public String toString()
    {
        String methodBody = new String();
        for (BaseStatement statement : this.statements) {
            methodBody = methodBody.concat(statement.toString());
        }
        return methodBody;
    }

    
}