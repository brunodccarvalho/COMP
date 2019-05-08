package compiler.modules;

import static compiler.modules.CompilationStatus.Codes.OK;

/**
 * A collection of utility methods for all compiler modules.
 *
 * Not very idiomatic; to be refactored when a better alternative idea shows up.
 */
public class CompilationStatus {
  public class Codes {
    public static final int OK = 0;
    public static final int WARNINGS = 1;
    public static final int MINOR_ERRORS = 2;
    public static final int MAJOR_ERRORS = 3;
    public static final int FATAL = 4;
  }

  private int current = OK;

  /**
   * Possibly raise the current exit code status
   */
  public int update(int status) {
    if (current < status) current = status;
    return current;
  }

  /**
   * Return the current status
   */
  public int status() {
    return current;
  }
}
