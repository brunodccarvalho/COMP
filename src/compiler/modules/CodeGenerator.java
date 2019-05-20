package compiler.modules;

import compiler.codeGenerator.ClassHeader;
import compiler.codeGenerator.CodeGeneratorConstants;
import compiler.codeGenerator.Constructors;
import compiler.codeGenerator.FieldDefinitionsHeader;
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
        FieldDefinitionsHeader fieldDefinitions = new FieldDefinitionsHeader(data.jmmClass.getMembersList());
        Constructors constructors = new Constructors();
        MethodGenerator methods = new MethodGenerator(this.data);
        if(data.jmmClass.hasMain())
            this.writer.writeFile(classHeader.toString(), superHeader.toString(), fieldDefinitions.toString(), constructors.toString(), methods.toString());
        else
            this.writer.writeFile(classHeader.toString(), superHeader.toString(), fieldDefinitions.toString(), constructors.toString(), methods.toString(),CodeGeneratorConstants.DEFAULTMAIN);
    }
}
