package compiler.codeGenerator;

import compiler.dag.DAGNewClass;
import compiler.symbols.ClassDescriptor;

/**
 * NewClass
 */
public class NewClass extends MethodBodyContent {

    private static String NEWCLASS = "\n\tnew ?\n\tdup\n\tinvokespecial ?/<init>()V";

    private ClassDescriptor className;

    public NewClass(Function function, DAGNewClass variable)
    {
        super(function);
        this.className = variable.getClassDescriptor();
    }

    @Override
    public String toString()
    {
        return subst(NewClass.NEWCLASS, this.className.toString(), this.className.toString());
    }

}
