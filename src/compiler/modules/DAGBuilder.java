package compiler.modules;

import compiler.dag.DAGMulti;
import compiler.dag.NodeFactory;
import compiler.symbols.FunctionLocals;
import compiler.symbols.JMMFunction;
import jjt.SimpleNode;

class DAGBuilder extends CompilationStatus {
  private final CompilationData data;

  DAGBuilder(CompilationData data) {
    this.data = data;
  }

  DAGBuilder buildMethods(CompilationStatus tracker) {
    buildMethods();
    tracker.update(status());
    return this;
  }

  void buildMethods() {
    for (JMMFunction function : data.localsMap.keySet()) {
      FunctionLocals locals = data.localsMap.get(function);
      SimpleNode functionNode = data.nodesMap.get(function);

      DAGMulti body = new NodeFactory(locals, this).buildMethod(functionNode);
      assert body != null;

      data.bodiesMap.put(function, body);
    }
  }

  String getPrint() {
    StringBuilder string = new StringBuilder();

    string.append("\n\n=== CLASS " + data.jmmClass.getName() + " DAGs ===\n\n");

    for (JMMFunction function : data.bodiesMap.keySet()) {
      DAGMulti body = data.bodiesMap.get(function);
      string.append(">>> DAG: " + function).append('\n').append(body).append("\n\n");
    }

    return string.toString();
  }

  void dump() {
    System.out.println(getPrint());
  }
}
