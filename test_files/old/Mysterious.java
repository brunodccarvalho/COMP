class Mysterious {
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

  public int f(int x, int y, int z, int w) {
    return x + y + z + w;
  }

  public int f(int x, int[] y, int z, int[] w) {
    return x + y[0] + z + w[0];
  }

  public int[] f(int x, int y, int[] z, int w) {
    return z;
  }

  public boolean f(int x, int y, int z, int[] w) {
    return x < y && w[0] < z;
  }

  public Mysterious f(boolean x, int[] y, Mysterious z, int w) {
    return new Mysterious();
  }

  public File f(boolean x, int y, int[] z, int w) {
    return new File();
  }

  public boolean f(boolean x, int[] y, int z, int[] w) {
    return true;
  }

  public Mysterous f(int x, boolean y, boolean z, int w) {
    return 1;
  }

  public int[] f(boolean x, boolean y, int z, int w) {
    return new int[10];
  }

  public Ok f(int x, boolean y, int z, int w) {
    return new Ok();
  }

  public Ok f(int x, boolean y, int z, boolean w) {
    return new Ok();
  }

  public int f(Ok x, int y, int z, Lol w) {
    return 0;
  }

  public boolean f(Ok x, int y, boolean z, int[] w) {
    return false;
  }

  public int deduces() {
    int int_local;
    int[] int_array;
    Mysterious mysterious;
    Ok ok;
    File file;

    // One with return type Mysterious
    mysterious = this.f(Any.a(), Any.b(), Any.c(), Any.d());

    // One taking a boolean last argument (and returning Ok)
    ok = this.f(Thread.getA(), Thread.getB(), Thread.getC(), true);

    // One taking an int last argument (and returning Ok)
    ok = this.f(Thread.getD(), Thread.getE(), Thread.getF(), 0);

    // One taking boolean third argument
    this.f(Any.a(), Any.b(), 1 < 2, Any.d());

    // Recursive
    this.f(this.f(Any.a(), true, Any.b(), Any.c()), Any.b(), 1 < 2, Any.d());

    // Full depth
    // clang-format off
    this.f(              // int
      this.f(              // ! Ok
        Any.a(),             // ! int
        this.f(              // ! boolean
          Any.z(),             // ! boolean
          int_array,           // * int[]
          this.f(              // ! int
            0,                   // * int
            Any.l(),             // ?
            Any.t(),             // ?
            1                    // * int
          ),
          Any.k()              // int[]
        ),
        Any.c(),             // ! int
        true                 // * boolean
      ),
      Any.u(),             // int
      true,                // * boolean
      Any.r()              // int[]
    );

    // clang-format on

    return 0;
  }
}
