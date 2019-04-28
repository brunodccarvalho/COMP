package compiler.codeGenerator;

import compiler.codeGenerator.Config;
import compiler.dag.BinaryOperator;
import compiler.dag.DAGAssignment;
import compiler.dag.DAGBinaryOp;
import compiler.dag.DAGCall;
import compiler.dag.DAGExpression;
import compiler.dag.DAGIntegerConstant;
import compiler.dag.DAGNode;
import compiler.dag.DAGVariable;
import compiler.symbols.JMMClassDescriptor;
import compiler.symbols.JMMMethodDescriptor;
import compiler.symbols.Descriptor;
import compiler.symbols.LocalDescriptor;
import compiler.symbols.ParameterDescriptor;
import compiler.symbols.TypeDescriptor;
import compiler.symbols.VariableDescriptor;
import compiler.modules.MethodBody;
import compiler.modules.SymbolsTable;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.lang.model.util.ElementScanner6;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Code generator for a single class Expects: - The class symbol table outputs:
 * - The .j (JVM code) file corresponding to the given class
 */
public class CodeGenerator {

    private JMMClassDescriptor classDescriptor;
    HashMap<JMMMethodDescriptor, MethodBody> methodBodies;
    private int numberLocals;
    private int numberTemp;
    private int numberParam;
    private SymbolsTable symbolsTable;
    private HashMap<Descriptor, Integer> variablesIndexes;
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
    private String getMethodDescriptor(JMMMethodDescriptor method) {
        String methodDescriptor = new String();
        TypeDescriptor[] typeDescriptors = method.getParameterTypes();
        for(TypeDescriptor typeDescriptor: typeDescriptors) {
            String jvmType = subst(CodeGeneratorConstants.METHODDESCRIPTOR, getType(typeDescriptor));
            methodDescriptor = methodDescriptor.concat(jvmType);
        }
        return methodDescriptor;
    }

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

    /**
     * @return The method's signature, like: <class_name>/<method_name>(<method_descriptor>)<return_type>
     */
    private String generateMethodSignature(JMMMethodDescriptor method) {
        String methodClass = this.classDescriptor.getClassName();
        String methodName = method.getName();
        String methodDescriptor = getMethodDescriptor(method);
        String returnType = getType(method.getReturnType());
        String methodSignature = subst(CodeGeneratorConstants.METHODSIGNATURE, methodClass, methodName, methodDescriptor, returnType);
        return methodSignature;
    }

    /**
     * @return The method's header, like: .method public <method_signature>\n?\n\treturn\n.end method
     */
    private String generateMethodHeader(JMMMethodDescriptor method) {
        String methodSignature = this.generateMethodSignature(method);
        String methodHeader = subst(CodeGeneratorConstants.METHOD, methodSignature);
        return methodHeader;
    }

    /**
     * Assigns an index in the method's array to the method's parameters
     */
    private void generateParamDeclaration(JMMMethodDescriptor method) {
        ParameterDescriptor[] parameters = method.getParameters();
        for(ParameterDescriptor parameter: parameters) {
            if(!this.variablesIndexes.containsKey(parameter)) {
                this.variablesIndexes.put(parameter, this.numberParam);
                numberParam++;
            }
        }
    }

    /**
     * Assigns an index in the method's array to the method's local variables
     */
    private void generateMethodVarDeclaration(JMMMethodDescriptor method) {
        HashMap<String, LocalDescriptor> locals = symbolsTable.getFunctionLocals(method).getVariables();
        int arrayIndex = this.numberParam;
        for(LocalDescriptor local : locals.values()) {
            if(!this.variablesIndexes.containsKey(local)) {
                this.variablesIndexes.put(local, arrayIndex);
                arrayIndex++;
                this.numberLocals++;
            }
        }
    }

    /**
     * @return The JVM store instruction (like istore 1) corresponding to the given DAGVariable.
     */
    private String generateStore(DAGVariable variable) {
        VariableDescriptor variableDescriptor = variable.getVariable();
        System.out.println("--------> " + variable.getVariable().getName());
        Integer variableIndex = this.variablesIndexes.get(variableDescriptor);
        if(variableIndex == null) { // class field
            return "";
        }
        String variableType = variableDescriptor.getType().toString();
        String regexStore = CodeGeneratorConstants.store.get(variableType);
        if(regexStore == null)
            regexStore = CodeGeneratorConstants.STOREADDRESS;
        return subst(regexStore, String.valueOf(variableIndex)) + "\n";
    }

    private String generateAssignment(DAGAssignment assignment) {
        String assignmentBody = new String();
        DAGVariable variable = assignment.getVariable();
        DAGExpression expression = assignment.getExpression();
        String variableStore = this.generateStore(variable);
        String expressionBody = this.generateExpression(expression);
        assignmentBody = assignmentBody.concat(expressionBody).concat(variableStore);
        return assignmentBody;
    }

    private String generateLoad(DAGVariable variable) {
        VariableDescriptor variableDescriptor = variable.getVariable();
        Integer variableIndex = this.variablesIndexes.get(variableDescriptor);
        if(variableIndex == null) { // class field
            return "";
        }
        String variableType = variableDescriptor.getType().toString();
        String regexLoad = CodeGeneratorConstants.load.get(variableType);
        if(regexLoad == null)
            regexLoad = CodeGeneratorConstants.LOADADDRESS;
        return subst(regexLoad, String.valueOf(variableIndex)) + "\n";
    }

    private String generateOperator(BinaryOperator operator) {
        return CodeGeneratorConstants.binaryOperators.get(operator.toString()) + "\n";
    }

    private String generateIntegerPush(DAGIntegerConstant integerConstant) {
        String integerPushBody = new String();
        int integerConstantValue = integerConstant.getValue();
        if(integerConstantValue == -1) {
            integerPushBody = subst(CodeGeneratorConstants.PUSHCONST, "m1");
        }
        else if((integerConstantValue >= 0) && (integerConstantValue <= 5)) {
            integerPushBody = subst(CodeGeneratorConstants.PUSHCONST, String.valueOf(integerConstantValue));
        }
        else 
            integerPushBody = subst(CodeGeneratorConstants.PUSHINT, String.valueOf(integerConstantValue));
        return integerPushBody + "\n";
    }
    /*
    private String generateMethodCall(DAGCall methodCall) {

    }
    */
    private String generateExpression(DAGExpression expression) {
        String expressionBody = new String();
        if(expression instanceof DAGBinaryOp) {
            DAGExpression lhs = ((DAGBinaryOp)expression).getLhs();
            DAGExpression rhs = ((DAGBinaryOp)expression).getRhs();
            BinaryOperator operator = ((DAGBinaryOp)expression).getOperator();
            String lhsBody = generateExpression(lhs);
            String rhsBody = generateExpression(rhs);
            String operatorBody = generateOperator(operator);
            expressionBody = expressionBody.concat(lhsBody).concat(rhsBody).concat(operatorBody);
        }
        else if(expression instanceof DAGVariable) {
            String loadBody = generateLoad((DAGVariable)expression);
            expressionBody = expressionBody.concat(loadBody);
        }
        else if(expression instanceof DAGIntegerConstant) {
            String integerLoadBody = generateIntegerPush((DAGIntegerConstant)expression);
            expressionBody = expressionBody.concat(integerLoadBody);
        }
        else if(expression instanceof DAGCall) {
            /*String callBody = generateMethodCall((DAGCall)expression);
            expressionBody = expressionBody.concat(callBody);*/
        }
        return expressionBody;

    }

    private String generateMethodBody(JMMMethodDescriptor method) {
        String methodBody = new String();
        MethodBody body = methodBodies.get(method);
        this.generateMethodVarDeclaration(method);
        DAGNode[] statements = body.getStatements();
        for (DAGNode statement : statements) {
            if(statement instanceof DAGAssignment) {
                String assignmentBody = this.generateAssignment((DAGAssignment)statement);
                methodBody = methodBody.concat(assignmentBody);
            }
            else if(statement instanceof DAGExpression) {
                String expressionBody = this.generateExpression((DAGExpression)statement);
                methodBody = methodBody.concat(expressionBody);
            }
        }
        return methodBody;
    }

    private void generateMethods() {
        for (JMMMethodDescriptor method : methodBodies.keySet()) {
            String methodHeader = this.generateMethodHeader(method);
            this.generateParamDeclaration(method);
            this.generateMethodVarDeclaration(method);
            String methodBody = this.generateMethodBody(method);
            String methodStructure = subst(methodHeader, methodBody);
            writer.write(methodStructure + "\n\n");

            this.numberLocals = 0;
            this.numberParam = 0;
            this.numberTemp = 0;
            this.variablesIndexes.clear();
        }
    }

    private void flush() {
        this.writer.flush();
    }

    private void close() {
        this.writer.close();
    }

    public static void generateCode(JMMClassDescriptor classDescriptor, HashMap<JMMMethodDescriptor, MethodBody> methodBodies, SymbolsTable symbolsTable) {
        CodeGenerator codeGenerator = new CodeGenerator(classDescriptor, methodBodies, symbolsTable);

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