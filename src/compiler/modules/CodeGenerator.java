package compiler.modules;

import compiler.codeGenerator.ClassHeader;
import compiler.codeGenerator.Constructors;
import compiler.codeGenerator.MethodGenerator;
import compiler.codeGenerator.SuperHeader;
import compiler.codeGenerator.utils.JasminWriter;

public class CodeGenerator extends CompilationStatus {

    private final CompilationData data;
    private final JasminWriter writer;

    public CodeGenerator(CompilationData data) {
        this.data = data;
        this.writer = new JasminWriter(data.jmmClass.getClassName());
    }

    public void generateCode() {

        ClassHeader classHeader = new ClassHeader(data.jmmClass.getClassName());
        SuperHeader superHeader = new SuperHeader(data.jmmClass.getSuperClassName());
        Constructors constructors = new Constructors();
        MethodGenerator methods = new MethodGenerator(this.data);
        this.writer.writeFile(classHeader.toString(), superHeader.toString(), constructors.toString(), methods.toString());
    }
}
