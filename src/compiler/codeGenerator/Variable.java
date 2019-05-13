package compiler.codeGenerator;

import compiler.dag.DAGLocal;
import compiler.dag.DAGMember;
import compiler.dag.DAGParameter;
import compiler.dag.DAGVariable;

public class Variable extends MethodBodyContent {

    private DAGVariable variable;

    public Variable(Function function, DAGVariable variable) {
        super(function);
        this.variable = variable;
    }

    @Override
    public String toString() {

        String variableLoad = new String();

        if(variable instanceof DAGMember)
        {
            MemberLoad member = new MemberLoad((DAGMember)variable);
            variableLoad = member.toString();
        }
        else if(variable instanceof DAGLocal) {
            Load loadBody = new Load(this.function, (DAGLocal)variable);
            variableLoad = loadBody.toString();
        }
        else if(variable instanceof DAGParameter)
        {
            LoadParameter member = new LoadParameter((DAGParameter)variable);
            variableLoad = member.toString();
        }
        return variableLoad;
    }
}