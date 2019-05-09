package compiler.codeGenerator;

import compiler.dag.DAGAssignment;
import compiler.dag.DAGExpression;
import compiler.dag.DAGNode;
import compiler.modules.CompilationData;
import compiler.symbols.JMMMainDescriptor;

import java.util.ArrayList;

public class Main extends Function {

    private ArrayList<BaseStatement> statements;
    private int localsSize;

    Main(JMMMainDescriptor main, CompilationData data) {
        super(main, data);
        this.statements = new ArrayList<BaseStatement>();
        DAGNode[] mainNode = data.bodiesMap.get(main).getNodes();
        for (DAGNode statement : mainNode) {
            if(statement instanceof DAGAssignment) {
                Assignment assignment = new Assignment(this, (DAGAssignment)statement);
                this.statements.add(assignment);
            }
            else if(statement instanceof DAGExpression) {
                Expression expression = new Expression(this, (DAGExpression)statement);
                this.statements.add(expression);
            }
        }
        this.localsSize = 99;
    }

    @Override
    public String toString() {
        String accumulator = "";
        for(BaseStatement statement : statements) {
            accumulator = accumulator.concat(statement.toString());
        }
        String methodStack = subst(CodeGeneratorConstants.STACK, String.valueOf(localsSize));
        String methodLocals = subst(CodeGeneratorConstants.LOCALS, String.valueOf(localsSize));
        String mainBodyRegex = subst(CodeGeneratorConstants.MAIN, methodStack, methodLocals, accumulator);
        return mainBodyRegex;
    }
}