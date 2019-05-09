package compiler.codeGenerator;

import compiler.modules.CompilationData;
import compiler.symbols.JMMCallableDescriptor;
import compiler.symbols.JMMFunction;
import compiler.symbols.JMMMainDescriptor;
import compiler.symbols.JMMMethodDescriptor;

import java.util.ArrayList;

public class MethodGenerator {

    private ArrayList<Function> methodStructures;

    public MethodGenerator(CompilationData data) {
        this.methodStructures = new ArrayList<Function>();
        for (JMMFunction method : data.bodiesMap.keySet()) {
            assert(method instanceof JMMMainDescriptor || method instanceof JMMMethodDescriptor);
            if(method instanceof JMMMainDescriptor) {
                Main main = new Main((JMMMainDescriptor) method, data);
                this.methodStructures.add(main);
                continue;
            }
            Method methodStructure = new Method((JMMCallableDescriptor)method, data);
            this.methodStructures.add(methodStructure);
        }
    }

    @Override
    public String toString() {
        String methods = "";
        for (Function m : this.methodStructures)
            methods += m.toString() + "\n";
        return methods;
    }

}