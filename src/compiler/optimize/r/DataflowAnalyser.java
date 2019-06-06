package compiler.optimize.r;

import java.util.ArrayList;
import java.util.HashMap;

import compiler.dag.DAGAssignment;
import compiler.dag.DAGMulti;
import compiler.dag.DAGNode;
import compiler.symbols.VariableDescriptor;

public class DataflowAnalyser {

    private DAGMulti multiNodes;
    private HashMap<Integer, ArrayList<VariableDescriptor>> uses;
    private HashMap<Integer, ArrayList<VariableDescriptor>> defs;
    private HashMap<Integer, ArrayList<VariableDescriptor>> ins;
    private HashMap<Integer, ArrayList<VariableDescriptor>> outs;

    public DataflowAnalyser(DAGMulti multiNodes) {

        this.multiNodes = multiNodes;
        this.uses = new HashMap<>();
        this.defs = new HashMap<>();
        this.ins = new HashMap<>();
        this.outs = new HashMap<>();
        this.generateDefs();

        // this.generateUses();
    }

    public HashMap<Integer, ArrayList<VariableDescriptor>> getOuts() {
        return this.outs;
    }

    public HashMap<Integer, ArrayList<VariableDescriptor>> getIns() {
        return this.ins;
    }

    private void generateDefs() {
        
        DAGNode[] nodes = multiNodes.getNodes();
        for (DAGNode statement : nodes) {
            Integer nodeId = Integer.valueOf(statement.getId());
            if(statement instanceof DAGAssignment) {
                VariableDescriptor assignedVariable = ((DAGAssignment)statement).getVariable().getVariable();
                if(!this.defs.containsKey(nodeId))
                    this.defs.put(nodeId, new ArrayList<>());
                this.defs.get(Integer.valueOf(nodeId)).add(assignedVariable);
            }
        }
    }

    private void generateUses() {
        // TODO
    }

}