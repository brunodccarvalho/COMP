package compiler.optimize.r;

import java.util.ArrayList;
import java.util.HashMap;

import compiler.codeGenerator.Function;
import compiler.dag.DAGMulti;
import compiler.symbols.JMMFunction;
import compiler.symbols.VariableDescriptor;

public class VariableManager {

    private Function function;
    private int index;
    private DAGMulti multiNodes;
    private HashMap<VariableDescriptor, Integer> variablesIndexes;

    public VariableManager(Function function, JMMFunction methodDescriptor, int index) {
        this.function = function;
        this.multiNodes = function.getData().bodiesMap.get(methodDescriptor);
        this.index = index;
        this.variablesIndexes = new HashMap<>();
    }

    public void assignMinVariable() {
        // Dataflow Analysis
        DataflowAnalyser dataflowAnalyser = new DataflowAnalyser(this.multiNodes);

        HashMap<Integer, ArrayList<VariableDescriptor>> outs = dataflowAnalyser.getOuts();
        HashMap<Integer, ArrayList<VariableDescriptor>> ins = dataflowAnalyser.getIns();
        
        // Graph Coloring 
        // TODO
    }

    public HashMap<VariableDescriptor, Integer> getLocalsIndexes() {
        return this.variablesIndexes;
    }
}