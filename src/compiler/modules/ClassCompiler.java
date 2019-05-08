package compiler.modules;

import static jjt.jmmTreeConstants.JJTCLASSDECLARATION;
import static jjt.jmmTreeConstants.JJTPROGRAM;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import jjt.ParseException;
import jjt.SimpleNode;
import jjt.jmm;

public final class ClassCompiler extends CompilerModule {
  private final File sourcefile;
  private final CompilationData data;

  public ClassCompiler(File sourcefile) {
    this.sourcefile = sourcefile;
    this.data = new CompilationData(sourcefile);

    try {
      DiagnosticsHandler.self = new DiagnosticsHandler(sourcefile);
    } catch (IOException e) {
      System.err.println("File " + sourcefile.getName() + " was not found.");
      status(FATAL);
      return;
    }
  }

  // Parse source file
  public ClassCompiler parse() throws FileNotFoundException, ParseException {
    System.out.println(" ***** PARSE");
    SimpleNode rootNode = jmm.parseClass(sourcefile);
    assert rootNode.is(JJTPROGRAM);

    SimpleNode classNode = rootNode.jjtGetChild(0);
    assert classNode.is(JJTCLASSDECLARATION);

    data.classNode = classNode;

    return this;
  }

  // Construct all Symbols Tables; this does stages 1 and 2 of the compiler proper.
  public ClassCompiler buildSymbolTables() throws CompilationException {
    System.out.println(" ***** SYMBOLS");
    SymbolsTableBuilder builder = new SymbolsTableBuilder(data);
    builder.readClassHeader();
    builder.readClassMemberVariables();
    builder.readClassMethodDeclarations();
    builder.readMethodLocals();
    builder.dump();
    return this;
  }

  // Build the DAGs for each method
  public ClassCompiler buildInternalRepresentations() throws CompilationException {
    System.out.println(" ***** DAGs");
    MethodBodyBuilder builder = new MethodBodyBuilder(data);
    builder.buildMethods();
    builder.dump();
    return this;
  }
}
