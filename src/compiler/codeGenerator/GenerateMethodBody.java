package compiler.codeGenerator;

import compiler.dag.DAGAssignment;
import compiler.dag.DAGExpression;
import compiler.dag.DAGNode;
import compiler.symbols.JMMMethodDescriptor;

import java.util.HashMap;
import java.util.ArrayList;
/**
 * MethodBody
 */
public class GenerateMethodBody {

    private Method belongs;

    private ArrayList<BaseStatement> statements;

    GenerateMethodBody (Method belongs,JMMMethodDescriptor method)
    {
        this.belongs=belongs;
        this.statements = new ArrayList<BaseStatement>();
        MethodBody body = CodeGenerator.singleton.methodBodies.get(method);
        MethodVarDeclaration varDecl = new MethodVarDeclaration(this.belongs,method);
        DAGNode[] nodes = body.getStatements();
        for (DAGNode statement : nodes) {
            if(statement instanceof DAGAssignment) {
                Assignment assignment = new Assignment((DAGAssignment)statement);
                this.statements.add(assignment);
            }
            else if(statement instanceof DAGExpression) {
                Expression expression = new Expression((DAGExpression)statement);
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