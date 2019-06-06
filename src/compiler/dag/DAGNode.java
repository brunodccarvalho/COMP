package compiler.dag;

/**
 * Base class of all DAG nodes
 *
 * DAG = Directed Acyclid Graph internal representation
 */
public abstract class DAGNode {

    protected int id;
    protected static int currentId = 1;

    protected DAGNode() {
        this.id = currentId++;
    }

    public int getId() {
        return this.id;
    }

}
