package compiler.codeGenerator;

import compiler.codeGenerator.Config;
import compiler.modules.ClassCompiler;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Code generator for a single class Expects: - The class symbol table Outputs:
 * - The .j (JVM code) file corresponding to the given class
 */
public class CodeGenerator {
    private ClassCompiler classCompiler;
    private FileOutputStream classFile; // .j file

    public CodeGenerator(ClassCompiler classCompiler) {
        this.classCompiler = classCompiler;
        String className = classCompiler.getJMMClassDescriptor().getClassName();
        File file = new File(Config.classFilesPath, className + ".j");
        try {
            this.classFile = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            
        }
    }

}