// Torture ExpressionFactory, AssignmentFactory and NodeFactory with
// complex expressions and structures, hard to compile function calls,
// and invalid errors everywhere of all types!

class DAGTorture extends Torture {
  int mi1;
  int mi2;
  int mi3;
  int[] marray1;
  int[] marray2;
  int[] marray3;
  boolean mb1;
  boolean mb2;
  DAGTorture msingleton;
  IO mio;

  // a + b + c. CORRECT
  public int add(int a, int b, int c) {
    return a + b + c;
  }

  // (+ array[i])... CORRECT
  public int add(int[] array) {
    int sum;
    int i;
    sum = 0;
    i = 0;
    while (i < array.length) {
      sum = sum + array[i];
      i = i + 1;
    }
    return sum;
  }

  // a + (b ? c : 0) CORRECT
  public int add(int a, boolean b, int c) {
    int sum;
    if (b) {
      sum = a + c;
    } else {
      sum = a;
    }
    return sum;
  }

  // (a ? 1 : 0) + (b ? 2 : 1) + (c ? 3 : 4) CORRECT
  public int add(boolean a, boolean b, boolean c) {
    int sum;
    if (a) {
      sum = 1;
    } else {
      sum = 0;
    }
    if (b) {
      sum = sum + 2;
    } else {
      sum = sum + 1;
    }
    if (c) {
      sum = sum + 3;
    } else {
      sum = sum + 4;
    }
    return sum;
  }

  // a ? (b * c) : (b + c) CORRECT
  public int add(boolean a, int b, int c) {
    int value;
    if (a) {
      value = b * c;
    } else {
      value = b + c;
    }
    return value;
  }

  // (a < b) ? (+ array[i])... : (a + b + array[a + b]) CORRECT
  public int add(int a, int[] array, int b) {
    int value;
    if (a < b) {
      value = this.add(array);
    } else {
      value = a + this.add(a, b, array[a + b]);
    }
    return value;
  }

  // this.msingleton = singleton
  public DAGTorture set(DAGTorture singleton) {
    msingleton = singleton;
    return this;
  }

  // array[i] = c
  public int[] add(int[] array, int i, int c) {
    array[i] = c;
    return array;
  }

  public int deduce() {
    int integer;
    int[] intarray;
    boolean bool;
    DAGTorture torture;
    IO iovar;

    iovar = io.get();                                  // W1: deduce ret==IO
    torture = this.set(torture);                       // W2: --
    bool = Ext.fetch(intarray, integer);               // W3: deduce ret==boolean
    intarray = this.add(intarray, integer, io.put());  // W4: deduce io.put()==int
    External.free();                                   // W5: deduce ret==void

    // Errors:
    bool = io.get(io.put());      // E1: cannot deduce return type of io.put().
    External.get(io.what(1, 2));  // E2: cannot deduce return type of io.what().
    A.get(B.get(C.get()));        // E3: cannot deduce return types of B.get() and C.get()

    return 0;
  }

  public static void main(String[] args) {
    DAGTorture torture;
    int num;

    torture = new DAGTorture();
    num = torture.set(torture).add(1, 2, 3);
  }
}
