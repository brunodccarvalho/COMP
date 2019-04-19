package compiler;

// Sketch & ideas

// enum of descriptor types:
// int, bool, int[], class.

// A class descriptor may be populated if the class's contents (data members and methods) are known
// upfront, our it may need to be populatable as methods are 'deduced' from the source code
// provided. Suppose we are compiling class A and we find code like

// B b(1.0, true);
// b.increment(3.5).decrement(2);

// but we know nothing of class B. Then we should be able to infer (making a bold assumption) that
// class B has a constructor that takes a double and a boolean, and also a method called increment
// that takes a double. The return value of this increment call shall be some class C that has a
// method called decrement that takes an integer. We __cannot__ later find that another call to
// method increment() with a single argument of type double that returns, say, an integer, as that
// is incompatible with the above statement.

class Descriptor {
  private String typename;
  // ...

  Descriptor(String typename) {
    this.typename = typename;
  }

  // ...
}
