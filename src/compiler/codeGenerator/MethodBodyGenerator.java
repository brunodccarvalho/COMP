package compiler.codeGenerator;

import compiler.codeGenerator.utils.LabelGenerator;
import compiler.dag.DAGAssignment;
import compiler.dag.DAGBracketAssignment;
import compiler.dag.DAGExpression;
import compiler.dag.DAGIfElse;
import compiler.dag.DAGMulti;
import compiler.dag.DAGNode;
import compiler.dag.DAGReturn;
import compiler.dag.DAGWhile;
import compiler.symbols.JMMFunction;
import java.util.ArrayList;

public class MethodBodyGenerator {

    private Function function;
    private LabelGenerator labelGenerator;
    private ArrayList<BaseStatement> statements;

    private MethodBodyGenerator(Function function) {
        this.function = function;
        this.statements = new ArrayList<BaseStatement>();
    } 
    
    public MethodBodyGenerator(Function function, JMMFunction methodDescriptor)
    {
        this(function);
        this.labelGenerator = new LabelGenerator();
        DAGMulti multiNodes = function.data.bodiesMap.get(methodDescriptor);
        this.generateMultiStatements(multiNodes);
    }

    public MethodBodyGenerator(Function function, JMMFunction methodDescriptor, LabelGenerator labelGenerator)
    {
        this(function);
        this.labelGenerator = labelGenerator;
        DAGMulti multiNodes = function.data.bodiesMap.get(methodDescriptor);
        this.generateMultiStatements(multiNodes);
    }

    public MethodBodyGenerator(Function function, DAGNode statement) {
        this(function);
        this.labelGenerator = new LabelGenerator();
        this.generateStatement(statement);
    }

    public MethodBodyGenerator(Function function, DAGNode statement, LabelGenerator labelGenerator) {
        this(function);
        this.labelGenerator = labelGenerator;
        this.generateStatement(statement);
    }

    private void generateMultiStatements(DAGMulti multiNodes) {
        DAGNode[] nodes = multiNodes.getNodes();
        for (DAGNode statement : nodes)
            this.generateStatement(statement);
    }

    private void generateStatement(DAGNode statement) {
        BaseStatement baseStatement = null;
        if(statement instanceof DAGAssignment) {
            if(statement instanceof DAGBracketAssignment)
                baseStatement = new BracketAssignment(this.function, (DAGBracketAssignment)statement);
            else
                baseStatement = new Assignment(this.function, (DAGAssignment)statement);
        }
        else if(statement instanceof DAGExpression)
            baseStatement = new ExpressionStatement(this.function, (DAGExpression)statement);
        else if(statement instanceof DAGReturn)
            baseStatement = new MethodReturn(this.function, (DAGReturn)statement);
        else if(statement instanceof DAGIfElse)
            baseStatement = new IfElse(this.function, (DAGIfElse)statement, this.labelGenerator);
        else if(statement instanceof DAGWhile)
            baseStatement = new While(this.function, (DAGWhile)statement, this.labelGenerator);
        else if(statement instanceof DAGMulti)
            this.generateMultiStatements((DAGMulti)statement);
        if(baseStatement != null)
            this.statements.add(baseStatement);
    }

    @Override
    public String toString()
    {
        String methodBody = new String();
        for (BaseStatement statement : this.statements)
            methodBody = methodBody.concat(statement.toString());
        return methodBody;
    }
}