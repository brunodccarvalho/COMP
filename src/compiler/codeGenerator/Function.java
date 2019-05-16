package compiler.codeGenerator;

import java.util.Collection;
import java.util.HashMap;

import compiler.modules.CompilationData;
import compiler.symbols.JMMCallableDescriptor;
import compiler.symbols.JMMFunction;
import compiler.symbols.LocalDescriptor;
import compiler.symbols.ParameterDescriptor;
import compiler.symbols.VariableDescriptor;

public abstract class Function extends JVMInst {
    
    protected JMMFunction function;
    private int index;
    protected HashMap<VariableDescriptor, Integer> variablesIndexes;
    protected final CompilationData data;
    protected MethodBodyGenerator methodBody;

    public Function(JMMFunction function, CompilationData data) {
        this.index = 1;
        this.function = function;
        this.data = data;
        this.variablesIndexes = new HashMap<>();
        this.arrangeIndexes();
        this.methodBody = new MethodBodyGenerator(this, function);
    }

    private void arrangeIndexes() {
        if(this.function instanceof JMMCallableDescriptor) 
            this.arrangeParamIndexes();
        this.arrangeLocalsIndexes();
    }

    private void arrangeLocalsIndexes() {
        Collection<LocalDescriptor> locals = this.data.localsMap.get(this.function).getVariables().values();
        for(LocalDescriptor local: locals) 
            this.variablesIndexes.put(local, ++index);
    }

    private void arrangeParamIndexes() {
        assert(this.function instanceof JMMCallableDescriptor);
        ParameterDescriptor[] params = ((JMMCallableDescriptor)this.function).getParameters();
        for(ParameterDescriptor param: params)
            this.variablesIndexes.put(param, ++index);
    }
}