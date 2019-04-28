package compiler.modules;

import static jjt.jmmTreeConstants.JJTCLASSDECLARATION;
import static jjt.jmmTreeConstants.JJTPROGRAM;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import compiler.symbols.JMMClassDescriptor;
import compiler.symbols.JMMMethodDescriptor;

import compiler.DiagnosticsHandler;
import jjt.ParseException;
import jjt.SimpleNode;
import jjt.jmm;

// Still trying to figure out the best way of doing things here.
// I suppose a java class dedicated to parsing one JMM class is a good design.
// I would also presume a non-throwing constructor is good form here; log the result of the
// compilation to a set of variables that may be consulted somewhere else.

public final class ClassCompiler extends CompilerModule {
  SimpleNode classNode;
  JMMClassDescriptor jmmClass;
  HashMap<JMMMethodDescriptor, MethodBody> methodBodies;  // TODO

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

    // Construct Symbols Tables; this does stages 1 and 2.
    SymbolsTable symbolsTable = new SymbolsTable(this.classNode);
    if (status(symbolsTable.status()) >= MAJOR_ERRORS) return;

    this.jmmClass = symbolsTable.jmmClass;

    symbolsTable.dump();
  }
  /**
   * Returns the class descriptor
   * @return The class descriptor
   */
  public JMMClassDescriptor getJMMClassDescriptor() {
    return this.jmmClass;
  }
}
