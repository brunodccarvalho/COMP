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

  public ClassCompiler compile() {
    try {
      parse();
      if (onErrorLevel(Codes.MAJOR_ERRORS)) return this;

      buildSymbolTables();
      if (onErrorLevel(Codes.MAJOR_ERRORS)) return this;

      buildInternalRepresentations();
      if (onErrorLevel(Codes.MINOR_ERRORS)) return this;

      generateCode();

    } catch (CompilationException e) {
      System.err.println(e.getMessage());
      update(Codes.FATAL);
    }
    return this;
  }

  /**
   * 1. Parse source file with JJT's generated parser.
   * * Parser.
   */
  private void parse() {
    try {
      SimpleNode rootNode = jmm.parseClass(sourcefile);
      assert rootNode.is(JJTPROGRAM);

      SimpleNode classNode = rootNode.jjtGetChild(0);
      assert classNode.is(JJTCLASSDECLARATION);

      data.classNode = classNode;
    } catch (FileNotFoundException e) {
      throw new CompilationException(e);
    } catch (ParseException e) {
      throw new CompilationException("Parsing Error: " + e.getMessage(), e);
    }
  }

  /**
   * 2. Build all symbol tables.
   * * Compiler proper.
   */
  private void buildSymbolTables() {
    new SymbolsTableBuilder(data).read(this);//.dump();
  }

  /**
   * 3. Build the internal representation of each method.
   * * Compiler proper.
   */
  private void buildInternalRepresentations() {
    new DAGBuilder(data).buildMethods(this).dump();
  }

  /**
   * 4. Build the code representations for each of the methods.
   * * Assembler
   */
  public void generateCode() {
    new CodeGenerator(data).generateCode();
  }

  /**
   * Exit if the error code is too high.
   */
  private boolean onErrorLevel(int level) {
    if (status() >= level) {
      System.err.println("Compilation error(s) found.");
      return true;
    } else {
      return false;
    }
  }
}
