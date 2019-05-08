package compiler.modules;

import java.io.File;
import java.util.HashMap;

import compiler.dag.DAGMulti;
import compiler.symbols.FunctionLocals;
import compiler.symbols.JMMClassDescriptor;
import compiler.symbols.JMMFunction;
import jjt.SimpleNode;

public class CompilationData {
  
  public SimpleNode classNode;
  public JMMClassDescriptor jmmClass;
  public final HashMap<JMMFunction, SimpleNode> nodesMap = new HashMap<>();
  public final HashMap<JMMFunction, FunctionLocals> localsMap = new HashMap<>();
  public final HashMap<JMMFunction, DAGMulti> bodiesMap = new HashMap<>();
  public final File sourcefile;

  public CompilationData(File sourcefile) {
    this.sourcefile = sourcefile;
  }
}
