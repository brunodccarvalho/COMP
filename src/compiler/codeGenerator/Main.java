package compiler.codeGenerator;

import compiler.modules.CompilationData;
import compiler.symbols.JMMMainDescriptor;

public class Main extends Function {

    private int localsSize;

    Main(JMMMainDescriptor main, CompilationData data) {
        super(main, data);
        this.localsSize = 99;
    }

    @Override
    public String toString() {
        String methodStack = subst(CodeGeneratorConstants.STACK, String.valueOf(localsSize));
        String methodLocals = subst(CodeGeneratorConstants.LOCALS, String.valueOf(localsSize));
        String mainBodyRegex = subst(CodeGeneratorConstants.MAIN, methodStack, methodLocals, methodBody.toString());
        return mainBodyRegex;
    }
}