package compiler.codeGenerator;

import compiler.dag.DAGBranch;

public abstract class Conditional extends BaseStatement {

    protected DAGBranch branch;

    Conditional(Function function, DAGBranch branch) {
        super(function);
        this.branch = branch;
    }
}