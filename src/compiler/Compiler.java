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

    try {
      String source = args[args.length - 1];
      new ClassCompiler(new File(source)).compile();

    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
