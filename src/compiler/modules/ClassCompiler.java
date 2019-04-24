package compiler.modules;

import static jjt.jmmTreeConstants.JJTCLASSDECLARATION;
import static jjt.jmmTreeConstants.JJTPROGRAM;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import compiler.symbols.JMMClassDescriptor;
import compiler.symbols.MethodDescriptor;
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
  HashMap<MethodDescriptor, SimpleNode> methodNodeMap;

  public ClassCompiler(File sourcefile) {
    // this.sourcefile = sourcefile;

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

    SymbolsTable symbolsTable = new SymbolsTable(this.classNode);
    this.jmmClass = symbolsTable.jmmClass;

    symbolsTable.dump();
  }
}
