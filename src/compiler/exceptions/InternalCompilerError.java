package compiler.exceptions;

public class InternalCompilerError extends InternalError {
  private static final long serialVersionUID = 3401369278885725678L;

  public InternalCompilerError() {
    super("Internal compiler error occurred");
  }

  public InternalCompilerError(Throwable t) {
    super("Internal compiler error occurred", t);
  }

  public InternalCompilerError(String message, Throwable t) {
    super("Internal compiler error occurred: " + message, t);
  }

  public InternalCompilerError(String message) {
    super("Internal compiler error occurred: " + message);
  }
}
