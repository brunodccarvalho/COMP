package compiler;

import java.io.File;
import java.io.FileNotFoundException;

import jjt.ParseException;
import jjt.SimpleNode;
import jjt.jmm;

final class Compiler {
  public static void main(String[] args) {
    System.out.println("Hello World!");

    if (args.length == 0)
      return;

    try {
      SimpleNode node = jmm.parseFile(new File(args[0]));
      if (node != null) {
        node.dump("");
        // ...
      }
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
    }
  }
}
