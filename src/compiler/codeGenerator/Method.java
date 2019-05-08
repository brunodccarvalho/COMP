package compiler.codeGenerator;

import compiler.symbols.JMMMethodDescriptor;

import compiler.symbols.Descriptor;

import java.util.HashMap;
import java.util.ArrayList;
/**
 * Method
 */
public class Method extends BaseByteCode {

    
    protected int numberLocals;
    protected int numberTemp;
    protected int numberParam;
    protected HashMap<Descriptor, Integer> variablesIndexes;

    private MethodParam generateParamDeclaration;
    private MethodVarDeclaration generateMethodVarDeclaration;
    private MethodHeader methodHeader;
    private GenerateMethodBody methodBody;
    private MethodReturn methodReturn;

    Method (JMMMethodDescriptor method) {
        this.numberLocals = 0;
        this.numberParam = 0;
        this.numberTemp = 0;
        this.variablesIndexes.clear();
        this.generateParamDeclaration = new MethodParam(this,method);
        this.generateMethodVarDeclaration = new MethodVarDeclaration(this,method);
        this.methodHeader = new MethodHeader(this,method);
        this.methodBody = new GenerateMethodBody(this,method);
        this.methodReturn = new MethodReturn(this,CodeGenerator.singleton.methodBodies.get(method).getReturnExpression());
        /*String methodStructure = subst(methodHeader, methodBody.concat(methodReturn));
        return methodStructure;*/

    }

    @Override
    public String toString()
    {
        return "";
    }

}