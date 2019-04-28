package compiler.codeGenerator;

import compiler.codeGenerator.Config;
import compiler.dag.DAGNode;
import compiler.symbols.JMMClassDescriptor;
import compiler.symbols.JMMMethodDescriptor;
import compiler.symbols.TypeDescriptor;
import compiler.modules.MethodBody;

import java.io.PrintWriter;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Code generator for a single class Expects: - The class symbol table Outputs:
 * - The .j (JVM code) file corresponding to the given class
 */
public class CodeGenerator {

    private JMMClassDescriptor classDescriptor;
    HashMap<JMMMethodDescriptor, MethodBody> methodBodies;
    private PrintWriter writer; // .j file

    private static String subst(String regex, String... substitutes) {
        for(String substitute: substitutes) {
            regex = regex.replaceFirst("\\?", substitute);
        }
        return regex;
    }

    /**
     * @return A JVM type descriptor. One of: I, Z, [I, or L<class_name>
     */
    private static String getType(TypeDescriptor typeDescriptor) {
        String typeName = typeDescriptor.getName();
        String jvmType = CodeGeneratorConstants.types.get(typeName);
        return (jvmType != null) ? jvmType : subst(CodeGeneratorConstants.CLASSTYPE, typeName);
    }

    /**
     * @return A JVM method descriptor, like: I;[I;Z;
     */
    private static String getMethodDescriptor(JMMMethodDescriptor method) {
        String methodDescriptor = new String();
        TypeDescriptor[] typeDescriptors = method.getParameterTypes();
        for(TypeDescriptor typeDescriptor: typeDescriptors) {
            String jvmType = subst(CodeGeneratorConstants.METHODDESCRIPTOR, getType(typeDescriptor));
            methodDescriptor = methodDescriptor.concat(jvmType);
        }
        return methodDescriptor;
    }

    private CodeGenerator(JMMClassDescriptor classDescriptor, HashMap<JMMMethodDescriptor, MethodBody> methodBodies) {
        this.classDescriptor = classDescriptor;
        this.methodBodies = methodBodies;
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
        String classHeader = CodeGenerator.subst(CodeGeneratorConstants.CLASSNAME, this.classDescriptor.getClassName());
        writer.write(classHeader + "\n");
    }

    private void generateSuperHeader() {
        String superName = this.classDescriptor.getSuperClassName();
        if(superName == null)
            superName = CodeGeneratorConstants.DEFAULTSUPER;
        String superHeader = CodeGenerator.subst(CodeGeneratorConstants.SUPERNAME, superName);
        writer.write(superHeader + "\n\n");
    }

    private void generateConstructors() {
        // TODO: other constructors besides the default constructor
        writer.write(CodeGeneratorConstants.DEFAULTINITIALIZER + "\n\n");
    }

    private void generateMethodSignature(JMMMethodDescriptor method) {
        String methodClass = this.classDescriptor.getClassName();
        String methodName = method.getName();
        String methodDescriptor = getMethodDescriptor(method);
        String returnType = getType(method.getReturnType());
        String methodSignature = subst(CodeGeneratorConstants.METHODSIGNATURE, methodClass, methodName, methodDescriptor, returnType);
        String methodHeader = subst(CodeGeneratorConstants.METHOD, methodSignature);
        writer.write(methodHeader + "\n\n");
    }

    private void generateMethods() {
        for (JMMMethodDescriptor method : methodBodies.keySet()) {
            MethodBody body = methodBodies.get(method);

            this.generateMethodSignature(method);

            /*
            System.out.println(">>> Statements for " + method);
            
            DAGNode[] statements = body.getStatements();
            for (DAGNode statement : statements) {
              System.out.println("    " + statement);
            }
            System.out.println("   return " + body.returnExpression);*/
        }
    }

    private void flush() {
        this.writer.flush();
    }

    private void close() {
        this.writer.close();
    }

    public static void generateCode(JMMClassDescriptor classDescriptor, HashMap<JMMMethodDescriptor, MethodBody> methodBodies) {
        CodeGenerator codeGenerator = new CodeGenerator(classDescriptor, methodBodies);

        // generate class header
        codeGenerator.generateClassHeader();

        // generate super header
        codeGenerator.generateSuperHeader();

        // generate constructors
        codeGenerator.generateConstructors();

        // generate methods
        codeGenerator.generateMethods();
        codeGenerator.flush();
        codeGenerator.close();

    }



}