package compiler.codeGenerator;

import compiler.dag.DAGNew;

public abstract class NewObject extends MethodBodyContent {

    protected DAGNew dagNew;

    public NewObject(Function function, DAGNew dagNew) {
        super(function);
        this.dagNew = dagNew;
    }
}