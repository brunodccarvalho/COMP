package compiler.modules;

import static jjt.jmmTreeConstants.JJTCLASSDECLARATION;
import static jjt.jmmTreeConstants.JJTPROGRAM;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import compiler.symbols.FunctionLocals;
import compiler.symbols.JMMClassDescriptor;
import compiler.symbols.JMMMethodDescriptor;

import compiler.DiagnosticsHandler;
import compiler.codeGenerator.CodeGenerator;
import compiler.dag.DAGNode;
import jjt.ParseException;
import jjt.SimpleNode;
import jjt.jmm;

// Still trying to figure out the best way of doing things here.
// I suppose a java class dedicated to parsing one JMM class is a good design.
// I would also presume a non-throwing constructor is good form here; log the result of the
// compilation to a set of variables that may be consulted somewhere else.

public final class ClassCompiler extends CompilerModule {
  private SimpleNode classNode;
  private JMMClassDescriptor jmmClass;

  public ClassCompiler(File sourcefile) {
    try {
      DiagnosticsHandler.self = new DiagnosticsHandler(sourcefile);
    } catch (IOException e) {
      System.err.println("File " + sourcefile.getName() + " was not found.");
      status(FATAL);
      return;
    }

    // Parse source file
    try {
      SimpleNode rootNode = jmm.parseClass(sourcefile);
      assert rootNode.is(JJTPROGRAM);

      this.classNode = rootNode.jjtGetChild(0);
      assert this.classNode.is(JJTCLASSDECLARATION);
    } catch (FileNotFoundException e) {
      System.err.println("Input source " + sourcefile.getPath() + " cannot be used ...");
      status(FATAL);
      return;
    } catch (ParseException e) {
      System.err.println("Parsing error found - aborting compilation.");
      status(FATAL);
      return;
    }

    // Construct all Symbols Tables; this does stages 1 and 2.
    SymbolsTable symbolsTable = new SymbolsTable(this.classNode);
    if (status(symbolsTable.status()) >= MAJOR_ERRORS) return;

    this.jmmClass = symbolsTable.jmmClass;
    symbolsTable.dump();

    HashMap<JMMMethodDescriptor, MethodBody> methodBodies = new HashMap<>();

    // Construct the MethodBody for each member method.
    for (JMMMethodDescriptor method : symbolsTable.methodLocalsMap.keySet()) {
      FunctionLocals locals = symbolsTable.methodLocalsMap.get(method);
      SimpleNode methodNode = symbolsTable.methodNodesMap.get(method);
      assert locals != null && methodNode != null;

      MethodBody body = new MethodBody(locals, methodNode);
      status(body.status());

      methodBodies.put(method, body);

      if (status() >= FATAL) return;
    }

    // Dump MethodBody...
    System.out.println("\n\n=== CLASS " + jmmClass.getName() + " DAG LINES ===");

    for (JMMMethodDescriptor method : methodBodies.keySet()) {
      MethodBody body = methodBodies.get(method);
      System.out.println(">>> Statements for " + method);

      for (DAGNode statement : body.statements) {
        System.out.println("    " + statement);
      }
      System.out.println("   return " + body.returnExpression);
    }

    // Generate Code
    CodeGenerator.generateCode(this.jmmClass);

    // Do main() too..

    // Now that there is more compiler pipeline logic code, we should consider
    // restructuring MethodBody/SymbolsTable/ClassCompiler (moving around code)
  }

  public JMMClassDescriptor getJMMClassDescriptor() {
    return this.jmmClass;
  }
}
