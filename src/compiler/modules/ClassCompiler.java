package compiler.modules;

import static jjt.jmmTreeConstants.JJTCLASSDECLARATION;
import static jjt.jmmTreeConstants.JJTPROGRAM;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import compiler.exceptions.CompilationException;
import jjt.ParseException;
import jjt.SimpleNode;
import jjt.jmm;

public final class ClassCompiler extends CompilationStatus {
  private final File sourcefile;
  private final CompilationData data;

  public ClassCompiler(File sourcefile) {
    this.sourcefile = sourcefile;
    this.data = new CompilationData(sourcefile);

    try {
      DiagnosticsHandler.self = new DiagnosticsHandler(sourcefile);
    } catch (IOException e) {
      System.err.println("File " + sourcefile.getName() + " was not found.");
      throw new CompilationException(e.getMessage());
    }
  }

  /**
   * 1. Parse source file with JJT's generated parser.
   * * Parser.
   */
  public ClassCompiler parse() {
    try {
      SimpleNode rootNode = jmm.parseClass(sourcefile);
      assert rootNode.is(JJTPROGRAM);

      SimpleNode classNode = rootNode.jjtGetChild(0);
      assert classNode.is(JJTCLASSDECLARATION);

      data.classNode = classNode;
      return this;
    } catch (FileNotFoundException e) {
      throw new CompilationException(e);
    } catch (ParseException e) {
      throw new CompilationException(e);
    }
  }

  /**
   * 2. Build all symbol tables.
   * * Compiler proper.
   */
  public ClassCompiler buildSymbolTables() {
    new SymbolsTableBuilder(data).read(this).dump();
    return this;
  }

  /**
   * 3. Build the internal representation of each method.
   * * Compiler proper.
   */
  public ClassCompiler buildInternalRepresentations() {
    new DAGBuilder(data).buildMethods(this).dump();
    return this;
  }

  /**
   * 4. Deduce unknown function signatures.
   * * Compiler proper.
   */
  public ClassCompiler deduceSignatures() {
    // ...
    return this;
  }

  /**
   * 5. Build the code representations for each of the methods.
   * ... or something. Rename this...
   * * Assembler
   */
  public ClassCompiler buildCodeRepresentations() {
    // ...
    return this;
  }

  /**
   * Exit if the error code is too high.
   */
  public ClassCompiler exitOnError(int level) {
    if (status() >= level) {
      System.err.println("Compilation error(s) found, exiting.");
      System.exit(status());
    }
    return this;
  }
}
