class Example {
  int counter;
  int[] array;
  boolean inited;
  File file;

  public int getCounter() {
    int counter;
    counter = 2;
    return counter * 2;
  }

  public int[] getArray() {
    return array;
  }

  public boolean getInited(int a, int[] b, boolean c) {
    return inited;
  }

  public int increment(int add) {
    counter = counter + add;
    return add;
  }
}
