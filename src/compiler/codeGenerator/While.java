package compiler.codeGenerator;

import compiler.dag.DAGWhile;

public class While extends Conditional {

    public While(Function function, DAGWhile branch) {
        super(function, branch);
    }

    @Override
    public String toString() {
        // TODO
        return "";
    }
}