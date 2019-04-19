package compiler;

// Sketch

class SymbolTable {
  private SymbolTable parent;
  // map...

  SymbolTable() {
    // ...
  }

  SymbolTable(SymbolTable parent) {
    this();
    this.parent = parent;
  }

  // ...
}
