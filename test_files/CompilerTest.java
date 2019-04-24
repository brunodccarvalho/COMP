class Example {
  int counter;
  boolean counter;
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

  public File getFile(File f) {
    return file;
  }

  public int getFile(File f) {
    return file;
  }

  public Example setCounter(int newcounter) {
    counter = newcounter;
    return this;
  }

  public Example setFile(File newfile) {
    file = newfile;
    return this;
  }

  public int increment(int add) {
    counter = counter + add;
    return counter;
  }

  public int function(String str) {
    int a;
    int[] b;
    boolean c;
    File d;

    a = b.length;
    b = new int[5];
    c = true;
    d = new File();

    return 0;
  }

  public static void main(String[] args) {
    io.println(new Example().getCounter());
  }

  public static void main(String[] lol) {

  }
}
