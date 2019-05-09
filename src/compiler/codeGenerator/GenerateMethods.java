package compiler.codeGenerator;

import compiler.symbols.JMMMethodDescriptor;

import java.util.ArrayList;

/**
 * generateMethods
 */
public class GenerateMethods {

    private ArrayList<Method> methodStructures;

    public GenerateMethods() {
        this.methodStructures = new ArrayList<Method>();
        for (JMMMethodDescriptor method : CodeGenerator.singleton.methodBodies.keySet()) {
            Method methodStructure = new Method(method);
            this.methodStructures.add(methodStructure);
        }
    }

    @Override
    public String toString() {
        String devolver = "";
        for (Method m : this.methodStructures)
            devolver += m.toString() + "\n";
        return devolver;

    }

}