package compiler.modules;

import java.io.File;
import java.util.HashMap;

import compiler.dag.DAGMulti;
import compiler.symbols.FunctionLocals;
import compiler.symbols.JMMClassDescriptor;
import compiler.symbols.JMMFunction;
import jjt.SimpleNode;

public class CompilationData {
  SimpleNode classNode;
  JMMClassDescriptor jmmClass;
  final HashMap<JMMFunction, SimpleNode> nodesMap = new HashMap<>();
  final HashMap<JMMFunction, FunctionLocals> localsMap = new HashMap<>();
  final HashMap<JMMFunction, DAGMulti> bodiesMap = new HashMap<>();
  final File sourcefile;

  CompilationData(File sourcefile) {
    this.sourcefile = sourcefile;
  }
}
