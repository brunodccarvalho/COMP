class JavapExample {
  static int[] getIntArray() {
    return null;  // lmao
  }

  boolean NOP_boolean() {
    return true;
  }

  int NOP_int() {
    return 2;
  }

  void DAGAdd(int lhs, int rhs) {
    int $ = lhs + rhs;
  }

  void DAGSub(int lhs, int rhs) {
    int $ = lhs - rhs;
  }

  void DAGMul(int lhs, int rhs) {
    int $ = lhs * rhs;
  }

  void DAGDiv(int lhs, int rhs) {
    int $ = lhs / rhs;
  }

  void DAGLess(int lhs, int rhs) {
    boolean $ = lhs < rhs;
  }

  void DAGAnd(boolean lhs, boolean rhs) {
    boolean $ = lhs && rhs;
  }

  void DAGLength(int[] array) {
    int $ = array.length;
  }

  void DAGNot(boolean b) {
    boolean $ = !b;
  }

  void DAGBracket(int[] array, int index) {
    int $ = array[index];
  }

  void DAGNewClass() {
    JavapExample $ = new JavapExample();
  }

  void DAGNewIntArray(int count) {
    int[] $ = new int[count];
  }

  void DAGIntegerConstant() {
    int $0 = 3;         // iconst_3
    int $1 = -78;       // bipush -78
    int $2 = 110;       // bipush 110
    int $3 = -25000;    // sipush -25000
    int $4 = 30000;     // sipush 30000
    int $5 = -1000000;  // ldc -1000000
    int $6 = 2000000;   // ldc 2000000
  }

  void DAGBooleanConstant() {
    boolean $0 = false;
    boolean $1 = true;
  }

  int member;

  void DAGAssignmentMember(int value) {
    member = value;
  }

  void DAGAssignmentParameter(int param, int value) {
    param = value;
  }

  void DAGAssignmentLocal(int value) {
    int local = value;
  }

  int[] memberArray;

  void DAGBracketAssignmentMember(int index, int value) {
    memberArray[index] = value;
  }

  void DAGBracketAssignmentParameter(int[] param, int index, int value) {
    param[index] = value;
  }

  void DAGBracketAssignmentLocal(int index, int value) {
    int[] local = getIntArray();
    local[index] = value;
  }

  void methodF(int arg1, boolean arg2, int arg3) {}

  static void staticF(int arg1, boolean arg2, int arg3) {}

  int methodI(int arg1, boolean arg2, int arg3) {
    return 0;
  }

  static int staticI(int arg1, boolean arg2, int arg3) {
    return 0;
  }

  void DAGMethodCall() {
    methodF(10, true, 50);
  }

  void DAGStaticCall() {
    staticF(10, true, 50);
  }

  void DAGMethodCallInt() {
    int local = methodI(10, true, 50);
  }

  void DAGStaticCallInt() {
    int local = staticI(10, true, 50);
  }

  void DAGCallReturningIgnored() {
    NOP_int();
    NOP_int();
    NOP_int();
  }

  int IfElseStatementBoolean(boolean b) {
    int ret;

    if (b) {
      ret = 1;
    } else {
      ret = 2;
    }

    return ret;
  }

  int IfElseStatementLess(int a, int b) {
    int ret;

    if (a < b) {
      ret = 1;
    } else {
      ret = 2;
    }

    return ret;
  }

  int IfElseStatementAnd(boolean a, boolean b) {
    int ret;

    if (a && b) {
      ret = 1;
    } else {
      ret = 2;
    }

    return ret;
  }

  boolean while_boolean_b;
  int while_less_a, while_less_b;
  boolean while_and_a, while_and_b;

  int WhileStatementBoolean(boolean b) {
    int ret = 0;

    while (b) {
      b = NOP_boolean();
      ++ret;
    }

    return ret;
  }

  int WhileStatementLess(int a, int b) {
    int ret = 0;

    while (a < b) {
      a = NOP_int();
      b = NOP_int();
      ++ret;
    }

    return ret;
  }

  int WhileStatementAnd(boolean a, boolean b) {
    int ret = 0;

    while (a && b) {
      a = NOP_boolean();
      b = NOP_boolean();
      ++ret;
    }

    return ret;
  }

  void ReturnVoid() {
    return;
  }

  int ReturnInt() {
    return 1;
  }

  int[] ReturnReference() {
    return new int[1];
  }

  boolean assignmentBoolean(boolean a) {
    boolean b = a;
    return b;
  }

  int assignmentInt(int a) {
    int b = a;
    return b;
  }

  int[] assignmentIntArray(int[] a) {
    int[] b = a;
    return b;
  }

  JavapExample assignmentClass(JavapExample a) {
    JavapExample b = a;
    return b;
  }

  int commonSubexpressionInt(int a, int b) {
    return (a * b + 1) + (a * b + 1);
  }
}
