class Example {
  int counter;
  int[] array;
  boolean inited;
  File file;



  public int increment(int add) {
    counter = counter + add;
    return add;
  }
  public static void main(String[] args) {
    io.println(new Example().getCounter());
  }
}