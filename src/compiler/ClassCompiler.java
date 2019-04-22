package compiler;

import java.io.File;
import java.io.FileNotFoundException;

import compiler.symbols.JMMClassDescriptor;
import compiler.symbols.TypeDescriptor;
import jjt.ParseException;
import jjt.SimpleNode;
import jjt.jmm;

// Still trying to figure out the best way of doing things here.
// I suppose a java class dedicated to parsing one JMM class is a good design.
// I would also presume a non-throwing constructor is good form here; log the result of the
// compilation to a set of variables that may be consulted somewhere else.

final class ClassCompiler {
  private final File sourcefile;
  private SimpleNode classNode;
  private JMMClassDescriptor jmmClass;

  private int resultCode = 0;
  private Exception resultException;

  private static SimpleNode jjtGetChild(SimpleNode node, int i) {
    assert node != null && node.jjtGetNumChildren() > i && i >= 0;
    return (SimpleNode) node.jjtGetChild(i);
  }

  public int getResultCode() {
    return resultCode;
  }

  public Exception getResultException() {
    return resultException;
  }

  ClassCompiler(File sourcefile) {
    this.sourcefile = sourcefile;

    // Parse source file
    try {
      SimpleNode rootNode = jmm.parseClass(sourcefile);
      this.classNode = jjtGetChild(rootNode, 0);
    } catch (FileNotFoundException e) {
      System.err.println("Input source " + sourcefile.getPath() + " cannot be used ...");
      this.resultCode = 1;
      this.resultException = e;
      return;
    } catch (ParseException e) {
      System.err.println("Parsing error found - aborting compilation.");
      this.resultCode = 2;
      this.resultException = e;
      return;
    }

    assert this.classNode != null;

    // Parse ClassHeader: Get class name, extends clause, create jmmClass object.
    this.parseClassHeader();
    if (this.resultCode != 0)
      return;

    // Parse ClassBody's variable declarations.
    this.parseClassMemberVariables();
  }

  private void parseClassHeader() {
    // ClassHeader
    SimpleNode classHeader = jjtGetChild(classNode, 0);

    // ClassHeader > ClassType
    SimpleNode classType = jjtGetChild(classHeader, 0);

    String className = classType.jjtGetVal();
    if (TypeDescriptor.has(className))
      throw new IllegalArgumentException("Invalid class name " + className);

    // ClassHeader > Extends
    if (classHeader.jjtGetNumChildren() == 1) {
      this.jmmClass = new JMMClassDescriptor(className);
    } else {
      // Don't know how to handle extends
      this.jmmClass = new JMMClassDescriptor(className);
    }

    System.out.println("Created class with name " + this.jmmClass.getClassName());
  }

  private void parseClassMemberVariables() {
    // ClassBody
    SimpleNode classBody = jjtGetChild(classNode, 0);

    // ClassBody > ClassVarDeclarations
    SimpleNode classVarDeclarations = jjtGetChild(classBody, 0);

    // ClassVarDeclarations > ClassVarDeclaration
    for (int i = 0; i < classVarDeclarations.jjtGetNumChildren(); ++i) {
      SimpleNode varDeclaration = jjtGetChild(classVarDeclarations, i);
      // ...
    }
  }
}
