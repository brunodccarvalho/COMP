class JavapExample {
  static int[] getIntArray() {
    return null;
  }

  void DAGBinaryOp(int lhs, int rhs) {
    int $ = lhs + rhs;
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

  void DAGMethodCall() {
    methodF(10, true, 50);
  }

  void DAGStaticCall() {
    staticF(10, true, 50);
  }
}
