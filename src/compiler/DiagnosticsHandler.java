package compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Handles I/O logging of various compilation diagnostics, let it be
 * information, warnings or errors.
 *
 * TODO
 */
class DiagnosticsHandler {
  private final File file;
  private final ArrayList<String> lines;

  DiagnosticsHandler(File file) throws IOException {
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

  // ... similar logic to ParseException::initialize
}
