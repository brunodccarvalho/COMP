package compiler;

import java.io.File;
import java.io.FileNotFoundException;

import compiler.modules.ClassCompiler;
import compiler.modules.CompilationException;
import jjt.ParseException;

final class Compiler {
  private static Compiler compiler;

  public Compiler get() {
    return compiler;
  }

  public static void main(String[] args) {
    // Parse option arguments...
    if (args.length == 0) {
      System.out.println("Usage: java Compiler FILE");
      return;
    }

    String source = args[args.length - 1];
    ClassCompiler compiler = new ClassCompiler(new File(source));

    try {
      compiler.parse();
      compiler.buildSymbolTables();
      compiler.buildInternalRepresentations();
      compiler.generateCode();
    } catch (ParseException e) {
      System.err.println(e.getMessage());
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
    } catch (CompilationException e) {
      System.err.println(e.getMessage());
    }
  }
}
