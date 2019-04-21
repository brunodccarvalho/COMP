package compiler;

import java.io.File;
import java.io.FileNotFoundException;

import jjt.SimpleNode;
import jjt.jmm;

/**
 * Compiler class. An instance of this class is used to parse each compilation
 * unit. Various instances may be launched to parse several source files.
 *
 * TODO
 */
final class Compiler {
  public static void main(String[] args) {
    System.out.println("Hello World!");

    if (args.length == 0)
      return;

    try {
      SimpleNode node = jmm.parseClass(new File(args[0]));
      if (node != null) {
        node.dump("");
        // ...
      }
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
    }
  }
}
