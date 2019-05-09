package compiler.modules;

import static compiler.modules.CompilationStatus.Codes.OK;

public class CompilationStatus {
  public class Codes {
    // No errors.
    public static final int OK = 0;

    // A warning is thrown in various places, namely in return type overload resolution.
    public static final int WARNINGS = 1;

    // An error is minor when the current phase can replace the error with valid data and the output
    // of the current phase will still work as valid input to the next phase. For example, when an
    // integer literal is out of bounds.
    public static final int MINOR_ERRORS = 2;

    // An error is major when it means that the output of the current phase, that will be used as
    // input to the next phase, does not meet the next phase's requirements and preconditions. For
    // example, when a function invokation statement is invalid, and the method does not exist.
    public static final int MAJOR_ERRORS = 3;

    // An error is fatal when a CompilationException is thrown, and it means that the current
    // compilation phase cannot keep going and must abort. For example, when the declared JMM class
    // name is invalid or already used.
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
