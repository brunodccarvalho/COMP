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

  // Any expression node
  public static void typeMismatch(SimpleNode node, TypeDescriptor expectedType,
                                  TypeDescriptor foundType) {
    printError(node, "Type mismatch: expected type " + expectedType + ", but found " + foundType);
  }

  // Identifier node
  public static void unresolvedVarName(SimpleNode node, String varName) {
    printError(node, varName + " cannot be resolved to a variable");
  }

  // Member variable node
  public static void memberAlreadyDefined(SimpleNode node, String varName) {
    printError(node, "Class member " + varName + " has already been defined here");
  }

  // Function node
  public static void methodAlreadyDefined(SimpleNode node, String methodName,
                                          FunctionSignature signature) {
    printError(node, "Class method " + methodName + signature + " has already been defined");
  }

  // Function node
  public static void conflictingParams(SimpleNode node, String methodName,
                                       FunctionSignature signature) {
    printError(node, "Class method " + methodName + signature + " has conflicting parameter names");
  }

  // Main node
  public static void mainMethodDefined(SimpleNode node) {
    printError(node, "Main method has already been defined here");
  }

  // Local variable node
  public static void localAlreadyDefined(SimpleNode node, String varName, JMMFunction method) {
    printError(node, "Name " + varName + " has already been locally defined");
  }

  // Function node
  public static void paramAlreadyDefined(SimpleNode node, String varName, JMMFunction method) {
    printError(node, "Locally defined " + varName + " is a parameter of " + method);
  }

  // Integer literal node
  public static void intLiteralOutOfRange(SimpleNode node, String intLiteral) {
    printError(node, "Integer literal out of range: " + intLiteral);
  }

  private static void printError(SimpleNode node, String message) {
    int errorLine = node.jjtGetFirstToken().beginLine;
    int errorCol = node.jjtGetFirstToken().beginColumn;
    String preamble = self.file.getName() + ":" + errorLine + ":" + errorCol + ": Error: ";
    System.err.println(preamble + message + ".");
    self.errorPointer(errorLine, errorCol);
  }

  private void errorPointer(int errorLine, int errorCol) {
    char[] data = new char[errorCol - 1];
    Arrays.fill(data, ' ');
    System.err.println(lines.get(errorLine - 1));
    for (int i = 0; i < errorCol - 1; i++) {
      System.err.print(" ");
    }
    System.err.println("^\n");
  }
}
