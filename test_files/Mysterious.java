public class Mysterious {
  int a;
  int b;
  int c;
  int d;
  int[] e;
  int[] f;
  int[] g;
  boolean h;
  boolean i;
  boolean j;
  Mysterious m1;
  Mysterious m2;

  public int deduces() {
    d = 1 + 1;
    return 0;
  }

  public int f1(int a, int b, int c) {
    return a + b * c;
  }

  public int f2(int a, int b, int[] c) {
    return a - b * c[2] + 2;
  }

  public int[] f3(int a, int[] b, boolean c) {
    e[a] = a + b[3];
    h = c;
    return f;
  }

  public boolean f4(int[] a, int b, int c) {
    g[a[b]] = c;
    return j;
  }
}
