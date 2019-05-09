package compiler.codeGenerator;

/**
 * BaseStatement
 */
public abstract class BaseStatement extends MethodBodyContent {

    public BaseStatement(Function function) {
        super(function);
    }
    
    @Override
    public abstract String toString();

}