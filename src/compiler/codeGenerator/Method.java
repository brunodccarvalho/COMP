package compiler.codeGenerator;

import compiler.modules.CompilationData;
import compiler.symbols.JMMCallableDescriptor;

public class Method extends Function {
    
    protected int numberLocals;
    protected int numberTemp;
    protected int numberParam;
    protected CompilationData data;
    private MethodHeader methodHeader;
    private MethodBodyGenerator methodBody;

    public Method(JMMCallableDescriptor method, CompilationData data) {
        super(method, data);
        this.data = data;
        this.numberLocals = 0;
        this.numberParam = 0;
        this.numberTemp = 0;
        this.methodHeader = new MethodHeader(this, method);
        this.methodBody = new MethodBodyGenerator(this,method);
    }

    @Override
    public String toString()
    {
        String methodStructure = subst(methodHeader.toString(), methodBody.toString());
        return methodStructure;
    }

}