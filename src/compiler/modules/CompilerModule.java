package compiler.modules;

import static jjt.jmmTreeConstants.*;

import compiler.symbols.PrimitiveDescriptor;
import compiler.symbols.TypeDescriptor;
import jjt.SimpleNode;

/**
 * A collection of utility methods for all compiler modules.
 *
 * Not very idiomatic; to be refactored when a better alternative idea shows up.
 */
public class CompilerModule {
  protected static int OK = 0;
  protected static int WARNINGS = 1;
  protected static int MINOR_ERRORS = 2;
  protected static int MAJOR_ERRORS = 3;
  protected static int FATAL = 4;

  private int currentCompilationStatus = OK;

  protected int status(int status) {
    if (this.currentCompilationStatus < status) this.currentCompilationStatus = status;
    return this.currentCompilationStatus;
  }

  public int status() {
    return this.currentCompilationStatus;
  }

  protected TypeDescriptor getOrCreateTypeFromNode(SimpleNode node) {
    assert node.is(JJTINT) || node.is(JJTINTARRAY) || node.is(JJTBOOLEAN) || node.is(JJTCLASSTYPE);

    switch (node.getId()) {
    case JJTINT:
      return PrimitiveDescriptor.intDescriptor;
    case JJTINTARRAY:
      return PrimitiveDescriptor.intArrayDescriptor;
    case JJTBOOLEAN:
      return PrimitiveDescriptor.booleanDescriptor;
    case JJTCLASSTYPE:
      return TypeDescriptor.getOrCreate(node.jjtGetVal());
    }

    // We should never arrive here.
    assert false;
    return null;
  }
}
