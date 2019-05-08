package compiler.modules;

public class CompilationException extends Exception {
  private static final long serialVersionUID = -5433204081428821217L;

  public CompilationException(String message) {
    super("Compilation fatal error: " + message);
  }
}
