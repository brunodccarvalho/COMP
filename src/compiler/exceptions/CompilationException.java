package compiler.exceptions;

public class CompilationException extends RuntimeException {
  private static final long serialVersionUID = 3921682026076379061L;

  public CompilationException(Throwable t) {
    super("Fatal error", t);
  }

  public CompilationException(String message, Throwable t) {
    super("Fatal error: " + message, t);
  }

  public CompilationException(String message) {
    super("Fatal error: " + message);
  }
}
