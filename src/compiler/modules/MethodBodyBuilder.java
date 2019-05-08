package compiler.modules;

import compiler.dag.DAGMulti;
import compiler.dag.NodeFactory;
import compiler.symbols.FunctionLocals;
import compiler.symbols.JMMFunction;
import jjt.SimpleNode;

// This class needs a different name, like 'BuilderDAG' or something...
class MethodBodyBuilder extends CompilerModule {
  final CompilationData data;

  MethodBodyBuilder(CompilationData data) {
    this.data = data;
  }

  void buildMethods() {
    for (JMMFunction function : data.localsMap.keySet()) {
      FunctionLocals locals = data.localsMap.get(function);
      SimpleNode functionNode = data.nodesMap.get(function);
      assert locals != null && functionNode != null;

      DAGMulti body = new NodeFactory(locals).buildMethod(functionNode);

      data.bodiesMap.put(function, body);
    }
  }

  void dump() {
    System.out.println("\n\n=== CLASS " + data.jmmClass.getName() + " DAGs ===");

    for (JMMFunction function : data.bodiesMap.keySet()) {
      DAGMulti body = data.bodiesMap.get(function);
      System.out.println(">>> DAG: " + function);
      System.out.println(body.toString());
    }
  }
}
