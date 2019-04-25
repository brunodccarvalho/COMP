package compiler.dag;

public enum BinaryOperator {
  PLUS("+"),
  MINUS("-"),
  TIMES("*"),
  DIVIDE("/"),
  LESS("<"),
  AND("&&");

  private String str;

  BinaryOperator(String s) {
    this.str = s;
  }

  @Override
  public String toString() {
    return str;
  }
}
