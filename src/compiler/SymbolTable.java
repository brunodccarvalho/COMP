package compiler;

// Sketch

// Idea: enum of symbol table types:
// classes, class data members, class methods, function parameters, local variables (depth i=0,1,...)

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
