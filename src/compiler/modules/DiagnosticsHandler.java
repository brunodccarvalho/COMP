package compiler.modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import compiler.symbols.FunctionSignature;
import compiler.symbols.TypeDescriptor;
import compiler.symbols.JMMFunction;
import jjt.SimpleNode;
import java.util.Arrays;

/**
 * Handles I/O logging of various compilation diagnostics, let it be
 * information, warnings or errors.
 *
 * TODO
 */
public class DiagnosticsHandler {
  private final File file;
  private final ArrayList<String> lines;
  public static DiagnosticsHandler self;

  public DiagnosticsHandler(File file) throws IOException {
    this.file = file;
    this.lines = new ArrayList<>();

    // Cache file
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        lines.add(line);
      }
    }
  }

  public void errorPointer(int errorLine, int errorCol) {
    char[] data = new char[errorCol - 1];
    Arrays.fill(data, ' ');
    System.err.println(lines.get(errorLine - 1));
    for (int i = 0; i < errorCol - 1; i++) {
      System.err.print(" ");
    }
    System.err.println("^\n");
  }

  public static void typeMismatch(SimpleNode node, TypeDescriptor varType,
                                  TypeDescriptor expressionType) {
    int errorLine = node.jjtGetFirstToken().beginLine;
    int errorCol = node.jjtGetFirstToken().beginColumn;
    System.err.println(self.file.getName() + ":" + errorLine + ": Error: "
                       + "Type mismatch: expected type " + varType + ", but expression has type "
                       + expressionType);
    self.errorPointer(errorLine, errorCol);
  }

  public static void unresolvedVarName(SimpleNode node, String varName) {
    int errorLine = node.jjtGetFirstToken().beginLine;
    int errorCol = node.jjtGetFirstToken().beginColumn;
    System.err.println(self.file.getName() + ":" + errorLine + ": Error: " + varName
                       + " cannot be resolved to a variable");
    self.errorPointer(errorLine, errorCol);
  }

  public static void varAlreadyDefined(SimpleNode node, String varName) {
    int errorLine = node.jjtGetFirstToken().beginLine;
    int errorCol = node.jjtGetFirstToken().beginColumn;
    System.err.println(self.file.getName() + ":" + errorLine + ": Error: Class member " + varName
                       + " is already defined");
    self.errorPointer(errorLine, errorCol);
  }

  public static void methodAlreadyDefined(SimpleNode node, String methodName,
                                          FunctionSignature signature) {
    int errorLine = node.jjtGetFirstToken().beginLine;
    int errorCol = node.jjtGetFirstToken().beginColumn;
    System.err.println(self.file.getName() + ":" + errorLine + ": Error: Class method " + methodName
                       + signature + " is already defined");
    self.errorPointer(errorLine, errorCol);
  }

  public static void conflictingParams(SimpleNode node, String methodName,
                                       FunctionSignature signature) {
    int errorLine = node.jjtGetFirstToken().beginLine;
    int errorCol = node.jjtGetFirstToken().beginColumn;
    System.err.println(self.file.getName() + ":" + errorLine + ": Error: Class method " + methodName
                       + signature + " has conflicting parameter names");
    self.errorPointer(errorLine, errorCol);
  }

  public static void mainMethodDefined(SimpleNode node) {
    int errorLine = node.jjtGetFirstToken().beginLine;
    int errorCol = node.jjtGetFirstToken().beginColumn;
    System.err.println(self.file.getName() + ":" + errorLine
                       + ": Error: Main method already defined");
    self.errorPointer(errorLine, errorCol);
  }

  public static void localAlreadyDefined(SimpleNode node, String varName, JMMFunction method) {
    int errorLine = node.jjtGetFirstToken().beginLine;
    int errorCol = node.jjtGetFirstToken().beginColumn;
    System.err.println(self.file.getName() + ":" + errorLine + ": Error: " + varName
                       + " is already locally defined in " + method);
    self.errorPointer(errorLine, errorCol);
  }

  public static void paramAlreadyDefined(SimpleNode node, String varName, JMMFunction method) {
    int errorLine = node.jjtGetFirstToken().beginLine;
    int errorCol = node.jjtGetFirstToken().beginColumn;
    System.err.println(self.file.getName() + ":" + errorLine + ": Error: locally defined " + varName
                       + " is a parameter of " + method);
    self.errorPointer(errorLine, errorCol);
  }

  public static void incompatibleTypes(SimpleNode node) {
    int errorLine = node.jjtGetFirstToken().beginLine;
    int errorCol = node.jjtGetFirstToken().beginColumn;
    System.err.println(self.file.getName() + ":" + errorLine
                       + ": Error: Main method already defined");
    self.errorPointer(errorLine, errorCol);
  }

  // ... similar logic to ParseException::initialize
}
