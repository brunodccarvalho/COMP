package compiler.modules;

import static jjt.jmmTreeConstants.*;
import static compiler.symbols.PrimitiveDescriptor.*;

import compiler.exceptions.InternalCompilerError;
import compiler.symbols.TypeDescriptor;
import jjt.SimpleNode;

/**
 * Generic static utility functions for use in various places.
 * Shorthand functions for handling type descriptors, SimpleNodes, etc.
 */
public class Utils {
  /**
   * @return The TypeDescriptor for this terminal SimpleNode. If the node is of Class type and the
   *     class name has never been seen before, it is created.
   */
  public static TypeDescriptor getOrCreateTypeFromNode(SimpleNode node) {
    assert node.is(JJTINT) || node.is(JJTINTARRAY) || node.is(JJTBOOLEAN) || node.is(JJTCLASSTYPE)
        || node.is(JJTIDENTIFIER);

    switch (node.getId()) {
    case JJTINT:
      return intDescriptor;
    case JJTINTARRAY:
      return intArrayDescriptor;
    case JJTBOOLEAN:
      return booleanDescriptor;
    case JJTCLASSTYPE:
    case JJTIDENTIFIER:
      return TypeDescriptor.getOrCreate(node.jjtGetVal());
    }

    // We should never arrive here.
    throw new InternalCompilerError();
  }

  /**
   * @return The simple node of type MethodBody which is the child of this function node.
   */
  public static SimpleNode getMethodBodyNode(SimpleNode methodNode) {
    assert methodNode.is(JJTMETHODDECLARATION) || methodNode.is(JJTMAINDECLARATION);

    SimpleNode bodyNode;

    if (methodNode.is(JJTMETHODDECLARATION)) {
      bodyNode = methodNode.jjtGetChild(3);
    } else {
      bodyNode = methodNode.jjtGetChild(1);
    }

    return bodyNode;
  }

  /**
   * @return The simple node of type ReturnStatement which is the child of this function node.
   */
  public static SimpleNode getReturnStatementNode(SimpleNode methodNode) {
    assert methodNode.is(JJTMETHODDECLARATION) || methodNode.is(JJTMAINDECLARATION);

    SimpleNode returnNode = null;

    if (methodNode.is(JJTMETHODDECLARATION)) {
      returnNode = methodNode.jjtGetChild(4);
    }

    return returnNode;
  }
}
