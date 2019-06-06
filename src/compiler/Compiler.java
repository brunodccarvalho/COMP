package compiler;

import java.io.File;

import compiler.modules.ClassCompiler;

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

    boolean optimizeR = false;
    String source = null;
    if(args.length == 1)
      source = args[0];
    else if(args.length == 2) {
      source = args[args.length - 2];
      optimizeR = (Integer.valueOf(args[1]) == 1);
    }
    new ClassCompiler(new File(source), optimizeR).compile();
  }
}
