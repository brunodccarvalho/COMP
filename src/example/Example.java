package example;

import java.io.File;

class Example {
  int counter = 100000000000000000;
  // Duplicate field Example.counter
  boolean counter;
  int[] array;
  boolean inited;
  File file;

  public int getCounter() {
    int counter = 2;
    return counter * 2;
  }

  public int[] getArray() {
    return array;
  }

  public boolean getInited() {
    return inited;
  }

  public File getFile(File f) {
    return file;
  }

  // Duplicate method getFile(File) in type Example
  public void getFile(File f) {
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
    int e;

    // The operator && is undefined for argument types int, int
    int f = a && e;

    int File;

    a = b.length;
    b = new int[5];
    c = true;
    d = new File(str);

    z = 2;

    b = new int[c];

    a = !0;

    return 0;
  }

  public static void main(String[] args) {
    io.println(new Example().getCounter());

    this.a = 2;
  }

  // Duplicate method main(String[]) in type Example
  public static void main(String[] lol) {}
}
