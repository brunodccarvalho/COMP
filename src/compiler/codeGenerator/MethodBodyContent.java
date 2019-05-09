package compiler.codeGenerator;

public abstract class MethodBodyContent extends JVMInst {

    /**
     * The function the content belongs to
     */
    protected Function function;

    protected MethodBodyContent(Function function) {
        this.function = function;
    }
}