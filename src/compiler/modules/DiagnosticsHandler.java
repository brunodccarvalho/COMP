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

  public static boolean SUPPRESS_WARNINGS = false;
  public static boolean SUPPRESS_NOTES = false;

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
    error(node, "Type mismatch: expected type " + expectedType + ", but found " + foundType);
  }

  // Identifier node
  public static void unresolvedVarName(SimpleNode node, String varName) {
    error(node, varName + " cannot be resolved to a variable");
  }

  // Member variable node
  public static void memberAlreadyDefined(SimpleNode node, String varName) {
    error(node, "Class member " + varName + " has already been defined here");
  }

  // Function node
  public static void methodAlreadyDefined(SimpleNode node, String methodName,
                                          FunctionSignature signature) {
    error(node, "Class method " + methodName + signature + " has already been defined");
  }

  // Function node
  public static void conflictingParams(SimpleNode node, String methodName,
                                       FunctionSignature signature) {
    error(node, "Class method " + methodName + signature + " has conflicting parameter names");
  }

  // Main node
  public static void mainMethodDefined(SimpleNode node) {
    error(node, "Main method has already been defined here");
  }

  // Local variable node
  public static void localAlreadyDefined(SimpleNode node, String varName, JMMFunction method) {
    error(node, "Name " + varName + " has already been locally defined");
  }

  // This node
  public static void staticUseOfThis(SimpleNode node) {
    error(node, "Cannot use 'this' in a static context");
  }

  // Function node
  public static void paramAlreadyDefined(SimpleNode node, String varName, JMMFunction method) {
    error(node, "Locally defined " + varName + " is a parameter of " + method);
  }

  // Integer literal node
  public static void intLiteralOutOfRange(SimpleNode node, String intLiteral) {
    error(node, "Integer literal out of range: " + intLiteral);
  }

  // Function call node
  public static void notOfObjectType(SimpleNode node) {
    error(node, "Expression used in function call is not of class type");
  }

  // Function call node
  public static void noSuchMethod(SimpleNode node, String methodName, String className) {
    error(node, "Class " + className + " has no method " + methodName);
  }

  // Function call node
  public static void noSuchStaticMethod(SimpleNode node, String staticName, String className) {
    error(node, "Class " + className + " has no static function " + staticName);
  }

  // Function call node
  public static void noOverload(SimpleNode node, String methodName, FunctionSignature signature,
                                String className) {
    error(node, "No overload of method " + methodName + " of class " + className
                    + " matches the signature " + methodName + signature);
  }

  // Function call node
  public static void noOverloadStatic(SimpleNode node, String staticName,
                                      FunctionSignature signature, String className) {
    error(node, "No overload of static function " + staticName + " of class " + className
                    + " matches the signature " + staticName + signature);
  }

  // Function call node
  public static void multipleOverloads(SimpleNode node, String methodName,
                                       FunctionSignature signature) {
    error(node, "Unsuccessful function overload resolution: Multiple overloads of method "
                    + methodName + " match the signature " + methodName + signature + "."
                    + " Please disambiguate your intended method call by assigning the result"
                    + " of external function call arguments to local variables, and using"
                    + " those local variables as arguments to this function call instead");
  }

  // Function call node
  public static void multipleOverloadsStatic(SimpleNode node, String staticName,
                                             FunctionSignature signature) {
    error(node, "Unsuccessful function overload resolution: Multiple overloads of static function "
                    + staticName + " match the signature " + staticName + signature + "."
                    + " Please disambiguate your intended method call by assigning the result"
                    + " of external function call arguments to local variables, and using"
                    + " those local variables as arguments to this function call instead");
  }

  // Parameter node
  public static void cantDeduceParameterType(SimpleNode node, String functionName) {
    error(node, "Resolution of parameter type in function " + functionName + " is impossible."
                    + " Please disambiguate the method signature by assigning the result of this"
                    + " external function call to a local variable, and using that local variable"
                    + " as argument to this function call instead");
  }

  // Function call node
  public static void deducedReturnType(SimpleNode node, TypeDescriptor returnType,
                                       String functionName, FunctionSignature signature) {
    note(node, "Deduced return type of function " + functionName + signature + " to " + returnType);
  }

  private static void error(SimpleNode node, String message) {
    print(node, message, "Error", ANSI_RED);
  }

  private static void warning(SimpleNode node, String message) {
    if (SUPPRESS_WARNINGS) return;
    print(node, message, "Warning", ANSI_PURPLE);
  }

  private static void note(SimpleNode node, String message) {
    if (SUPPRESS_NOTES) return;
    print(node, message, "note", ANSI_CYAN);
  }

  private static void print(SimpleNode node, String message, String header, String ansicolor) {
    int errorLine = node.jjtGetFirstToken().beginLine;
    int errorCol = node.jjtGetFirstToken().beginColumn;
    String preamble = self.file.getName() + ":" + errorLine + ":" + errorCol;
    preamble += ": " + ansicolor + header + ANSI_RESET + ": ";
    System.err.println(preamble + message + ".");
    self.pointer(errorLine, errorCol, ansicolor);
  }

  private void pointer(int errorLine, int errorCol, String ansicolor) {
    char[] data = new char[errorCol - 1];
    Arrays.fill(data, ' ');
    System.err.println(lines.get(errorLine - 1));
    for (int i = 0; i < errorCol - 1; i++) {
      System.err.print(" ");
    }
    System.err.println(ansicolor + "^" + ANSI_RESET + "\n");
  }

  private static final String ANSI_RESET = "\u001B[0m";
  private static final String ANSI_BLACK = "\u001B[30m";
  private static final String ANSI_RED = "\u001B[31m";
  private static final String ANSI_GREEN = "\u001B[32m";
  private static final String ANSI_YELLOW = "\u001B[33m";
  private static final String ANSI_BLUE = "\u001B[34m";
  private static final String ANSI_PURPLE = "\u001B[35m";
  private static final String ANSI_CYAN = "\u001B[36m";
  private static final String ANSI_WHITE = "\u001B[37m";
}
