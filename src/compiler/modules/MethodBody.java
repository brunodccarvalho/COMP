package compiler.modules;

import compiler.dag.DAGNode;
import compiler.symbols.FunctionLocals;
import compiler.symbols.MethodDescriptor;
import static jjt.jmmTreeConstants.*;
import jjt.SimpleNode;

public class MethodBody extends CompilerModule {
    
    private FunctionLocals functionLocals;
    private DAGNode[] statements;

    public MethodBody(FunctionLocals functionLocals, SimpleNode methodNode) {
        
        this.functionLocals = functionLocals;
        // construct DAGNodes - number nodes = N (Number of method body children) - M (number of var declarations)
        // to construct each DAG: new NodeFactory(locals).build(node);
        SimpleNode methodBodyNode = getMethodBodyNode(methodNode);
        int numberDecl = 0;
        int numberDeclExp = methodBodyNode.jjtGetNumChildren();
        for(int i = 0; i < numberDeclExp; i++) {
            if(methodBodyNode.jjtGetChild(i).is(JJTVARIABLEDECLARATION))
            numberDecl++;
        }
        int numberDAG = numberDeclExp - numberDecl;
        statements = new DAGNode[numberDAG];
        // DAG Expression Vs DAG Assignment
        numberDecl = 0;
        for(int i = 0; i < numberDeclExp; i++) {
            if(!methodBodyNode.jjtGetChild(i).is(JJTVARIABLEDECLARATION))
                //children[numDecl] = new NodeFactory(functionLocals).build(methodBodyNode);
        }
    }

    public static SimpleNode getMethodBodyNode(SimpleNode node) {
        assert(node.is(JJTMETHODDECLARATION) || node.is(JJTMAINDECLARATION));
        if(node.is(JJTMETHODDECLARATION))
            return node.jjtGetChild(3);
        return node.jjtGetChild(1); // main declaration
    }
}