package compiler.dag;

public class DAGBinaryOp extends DAGExpression {
  public enum BinaryOperator {
    PLUS("+"), MINUS("-"), TIMES("*"), DIVIDE("/"), LESS("<"), AND("&&");

    private String str;

    BinaryOperator(String s) {
      this.str = s;
    }

    @Override
    public String toString() {
      return str;
    }
  }

  private BinaryOperator op;
  private DAGExpression lhs;
  private DAGExpression rhs;
}
