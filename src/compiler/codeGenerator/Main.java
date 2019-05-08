package compiler.codeGenerator;

import compiler.dag.DAGAssignment;
import compiler.dag.DAGExpression;
import compiler.dag.DAGNode;
import compiler.modules.MethodBody;

import java.util.ArrayList;

/**
 * Main
 */
public class Main extends BaseByteCode {

    private ArrayList<BaseStatement> statements;
    private int localsSize;

    Main(MethodBody mainBody) {
        this.statements = new ArrayList<BaseStatement>();
        if (mainBody != null) {
            DAGNode[] mainNode = mainBody.getStatements();
            for (DAGNode statement : mainNode) {
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
        this.localsSize = 99;

    }

    @Override
    public String toString() {
        String devolver = "";
        for(BaseStatement statement : statements)
        {
            devolver = devolver.concat(statement.toString());
        }
        String methodStack = subst(CodeGeneratorConstants.STACK, String.valueOf(localsSize));
        String methodLocals = subst(CodeGeneratorConstants.LOCALS, String.valueOf(localsSize));
        String mainBodyRegex = subst(CodeGeneratorConstants.MAIN, methodStack, methodLocals, devolver);
        return mainBodyRegex;

    }
}