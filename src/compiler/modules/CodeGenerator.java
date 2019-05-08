package compiler.modules;

import compiler.codeGenerator.ClassHeader;
import compiler.codeGenerator.Constructors;
import compiler.codeGenerator.GenerateMethods;
import compiler.codeGenerator.Main;
import compiler.codeGenerator.SuperHeader;
import compiler.codeGenerator.utils.JasminWriter;
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

import java.io.PrintWriter;
import java.util.HashMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CodeGenerator extends CompilerModule {

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
        //GenerateMethods methods = new GenerateMethods();
        //Main main = new Main(mainBody);
        this.writer.writeFile(classHeader.toString(), superHeader.toString(), constructors.toString());
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
