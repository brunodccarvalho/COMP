package compiler.codeGenerator;

import compiler.dag.DAGAssignment;
import compiler.dag.DAGExpression;
import compiler.dag.DAGMulti;
import compiler.dag.DAGNode;
import compiler.symbols.JMMCallableDescriptor;
import java.util.ArrayList;

public class MethodBodyGenerator {

    private Method method;
    private ArrayList<BaseStatement> statements;

    MethodBodyGenerator(Method method, JMMCallableDescriptor methodDescriptor)
    {
        this.method = method;
        this.statements = new ArrayList<BaseStatement>();
        DAGMulti multiNodes = method.data.bodiesMap.get(methodDescriptor);
        MethodVarDeclaration varDecl = new MethodVarDeclaration(this.method, methodDescriptor);
        DAGNode[] nodes = multiNodes.getNodes();
        for (DAGNode statement : nodes) {
            if(statement instanceof DAGAssignment) {
                Assignment assignment = new Assignment(this.method, (DAGAssignment)statement);
                this.statements.add(assignment);
            }
            else if(statement instanceof DAGExpression) {
                Expression expression = new Expression(this.method, (DAGExpression)statement);
                this.statements.add(expression);
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