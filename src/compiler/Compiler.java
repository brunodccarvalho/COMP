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

    String source = args[args.length - 1];
    ClassCompiler unit = new ClassCompiler(new File(source));
  }
}
