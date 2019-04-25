package compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import jjt.Token;

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


  public void errorLine(Token t)
  {
    String position="Line " + t.beginLine + " , Column :" + t.beginColumn + " ";
    System.err.println(position + lines.get(t.beginLine-1));
    for(int i=0;i<t.beginColumn-1 + position.length();i++)
    {
      System.err.print(" ");
    }
    System.err.println("^\n");

  }

  // ... similar logic to ParseException::initialize
}
