package example;

import java.io.File;

class Example {
  int counter;
  int[] array;
  boolean inited;
  File file;

  public int getCounter() {
    return counter;
  }

  public int[] getArray() {
    return array;
  }

  public boolean getInited() {
    return inited;
  }

  public File getFile() {
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

    a = str.length();
    b = new int[5];
    c = true;
    d = new File(str);

    return 0;
  }

  public static int lol() {
    Example example = new Example();

    return example.counter;
  }
}
