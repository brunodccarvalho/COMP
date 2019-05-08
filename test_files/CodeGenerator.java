package compiler.codeGenerator;

import compiler.codeGenerator.Config;
import compiler.dag.BinaryOperator;
import compiler.dag.DAGAssignment;
import compiler.dag.DAGBinaryOp;
import compiler.dag.DAGCall;
import compiler.dag.DAGExpression;
import compiler.dag.DAGIntegerConstant;
import compiler.dag.DAGLocal;
import compiler.dag.DAGMember;
import compiler.dag.DAGNode;
import compiler.dag.DAGParameter;
import compiler.dag.DAGVariable;
import compiler.symbols.JMMClassDescriptor;
import compiler.symbols.JMMMainDescriptor;
import compiler.symbols.JMMMethodDescriptor;
import compiler.symbols.Descriptor;
import compiler.symbols.LocalDescriptor;
import compiler.symbols.ParameterDescriptor;
import compiler.symbols.TypeDescriptor;
import compiler.symbols.VariableDescriptor;
import compiler.symbols.MemberDescriptor;
import compiler.modules.MethodBody;
import compiler.modules.SymbolsTable;

import java.io.PrintWriter;
import java.util.HashMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * TODO:
 *  - generate main method (invokespecial)
 *  - load/store fields
 *  - test function invocation
 */
// clang-format off
/**
 * Code generator for a single class Expects: - The class symbol table outputs:
 * - The .j (JVM code) file corresponding to the given class
 */
public class CodeGenerator {

    JMMClassDescriptor classDescriptor;
    HashMap<JMMMethodDescriptor, MethodBody> methodBodies;
    private int numberLocals;
    private int numberTemp;
    private int numberParam;
    SymbolsTable symbolsTable;
    HashMap<Descriptor, Integer> variablesIndexes;
    private PrintWriter writer; // .j file

    public static CodeGenerator singleton;

    private CodeGenerator(JMMClassDescriptor classDescriptor, HashMap<JMMMethodDescriptor, MethodBody> methodBodies, SymbolsTable symbolsTable) {
        this.classDescriptor = classDescriptor;
        this.methodBodies = methodBodies;
        this.symbolsTable = symbolsTable;
        this.numberLocals = 0;
        this.numberParam = 0;
        this.numberTemp = 0;
        this.variablesIndexes = new HashMap<>();
        String className = classDescriptor.getClassName();
        File file = new File(Config.classFilesPath, className + ".j");
        File path = new File(Config.classFilesPath);
        try {
            if(!path.exists())path.mkdirs();
            if(!file.exists())file.createNewFile();
            this.writer = new PrintWriter(file);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("ERROR: could not generate .j file for class " + file.getAbsolutePath());
            System.exit(1);
        } catch(IOException e)
        {
            System.out.println("ERROR: could not create .j file for class " + file.getAbsolutePath());
            System.exit(1);
        }
    }

    private void flush() {
        this.writer.flush();
    }

    private void close() {
        this.writer.close();
    }
    
    public void write(String text)
    {
        this.writer.write(text);
    }

    public static void generateCode(JMMClassDescriptor classDescriptor, HashMap<JMMMethodDescriptor, MethodBody> methodBodies, SymbolsTable symbolsTable,MethodBody mainBody) {
        CodeGenerator codeGenerator = new CodeGenerator(classDescriptor, methodBodies, symbolsTable);

        CodeGenerator.singleton=codeGenerator;

        ClassHeader classHeader = new ClassHeader();
        codeGenerator.write(classHeader.toString() +"\n");

        SuperHeader superHeader = new SuperHeader();
        codeGenerator.write(superHeader.toString() + "\n");

        Constructors constructors = new Constructors();
        codeGenerator.write(constructors.toString() + "\n");

        GenerateMethods methods = new GenerateMethods();
        codeGenerator.write(methods.toString() + "\n");

        Main main = new Main(mainBody);
        codeGenerator.write(main.toString() + "\n");

        codeGenerator.flush();
        codeGenerator.close();
    }

    private static int dagLessLabelCounter = 0;

/*
    // Extra methods
    private String generateLessOperator(DAGBinaryOp dag) {
        assert dag.isComparison();
        DAGExpression lhs = dag.getLhs();
        DAGExpression rhs = dag.getRhs();

        String labelFalse = "FalseBranch_" + Integer.toString(++dagLessLabelCounter);
        String labelTrue = "TrueBranch_" + Integer.toString(++dagLessLabelCounter);

        String lhsCode = generateExpression(lhs); // does this have newline?
        String rhsCode = generateExpression(rhs); // does this have newline?

        // these don't have newline:
        String ifCode = subst(CodeGeneratorConstants.IF_ICMPGE, labelFalse);
        String gotoCode = subst(CodeGeneratorConstants.GOTO, labelTrue);
        String trueConst = CodeGeneratorConstants.ICONST_1 + "\n";
        String falseConst = CodeGeneratorConstants.ICONST_0 + "\n";

        return lhsCode + rhsCode + ifCode + trueConst + gotoCode +
               labelFalse + falseConst + labelTrue;
    }*/

}
