package compiler.codeGenerator;

import compiler.codeGenerator.Config;
import compiler.symbols.JMMClassDescriptor;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Code generator for a single class Expects: - The class symbol table Outputs:
 * - The .j (JVM code) file corresponding to the given class
 */
public class CodeGenerator {

    private JMMClassDescriptor classDescriptor;
    private PrintWriter writer; // .j file

    private static String subst(String regex, String[] substitutes) {
        for(String substitute: substitutes) {
            regex = regex.replaceFirst("\\?", substitute);
        }
        return regex;
    }

    private CodeGenerator(JMMClassDescriptor classDescriptor) {
        this.classDescriptor = classDescriptor;
        String className = classDescriptor.getClassName();
        File file = new File(Config.classFilesPath, className + ".j");
        try {
            this.writer = new PrintWriter(file);
        } catch(FileNotFoundException e) {
            System.out.println("ERROR: could not generate .j file for class " + file.getAbsolutePath());
            System.exit(1);
        }
    }

    private void generateClassHeader() {
        String[] substitutes = new String[]{this.classDescriptor.getClassName()};
        String classHeader = CodeGenerator.subst(CodeGeneratorConstants.CLASSNAME, substitutes);
        writer.write(classHeader + "\n");
    }

    private void generateSuperHeader() {
        String superName = this.classDescriptor.getSuperClassName();
        if(superName == null)
            superName = CodeGeneratorConstants.DEFAULTSUPER;
        String[] substitutes = new String[]{superName};
        String superHeader = CodeGenerator.subst(CodeGeneratorConstants.SUPERNAME, substitutes);
        writer.write(superHeader + "\n");
    }

    private void flush() {
        this.writer.flush();
    }

    private void close() {
        this.writer.close();
    }

    public static void generateCode(JMMClassDescriptor classDescriptor) {
        CodeGenerator codeGenerator = new CodeGenerator(classDescriptor);

        // generate class header
        codeGenerator.generateClassHeader();

        // generate super header
        codeGenerator.generateSuperHeader();

        codeGenerator.flush();
        codeGenerator.close();

    }



}