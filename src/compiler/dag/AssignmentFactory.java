package compiler.dag;

import static jjt.jmmTreeConstants.*;
import compiler.symbols.FunctionLocals;
import compiler.symbols.VariableDescriptor;
import jjt.SimpleNode;

/**
 * A Factory class that manages the construction of one DAGAssignment when a JJT
 * Assignment is found in the Abstract Syntax Tree. It knows how to instantiate
 * both DAGAssignment and DAGBracketAssignment subclasses.
 *
 * Two assignments are always different.
 */
public class AssignmentFactory extends BaseDAGFactory {
  protected final ExpressionFactory factory;

  public AssignmentFactory(FunctionLocals locals) {
    super(locals);
    this.factory = new ExpressionFactory(locals);
  }

  @Override
  public DAGAssignment build(SimpleNode assignmentNode) {
    assert assignmentNode.is(JJTASSIGNMENT);

    SimpleNode firstNode = assignmentNode.jjtGetChild(0);
    assert firstNode.is(JJTIDENTIFIER) || firstNode.is(JJTBRACKET);

    if (firstNode.is(JJTIDENTIFIER)) {
      return buildAssignment(assignmentNode);
    } else {
      return buildBracketAssignment(assignmentNode);
    }
  }

  private DAGAssignment buildAssignment(SimpleNode assignmentNode) {
    SimpleNode variableNode = assignmentNode.jjtGetChild(0);
    assert variableNode.is(JJTIDENTIFIER);

    DAGVariable var = this.buildVariable(variableNode);
    DAGExpression expression = this.factory.build(assignmentNode.jjtGetChild(1));
    return new DAGAssignment(var, expression);
  }

  private DAGBracketAssignment buildBracketAssignment(SimpleNode assignmentNode) {
    SimpleNode bracketsNode = assignmentNode.jjtGetChild(0);
    SimpleNode variableNode = bracketsNode.jjtGetChild(0);
    assert bracketsNode.is(JJTBRACKET) && variableNode.is(JJTIDENTIFIER);

    DAGVariable var = this.buildVariable(bracketsNode.jjtGetChild(0));
    DAGExpression indexExpression = this.factory.build(assignmentNode.jjtGetChild(1));
    DAGExpression assignedExpression = this.factory.build(assignmentNode.jjtGetChild(2));
    return new DAGBracketAssignment(var, assignedExpression, indexExpression);
  }

  /**
   * @SemanticError: Variable name cannot be resolved to a variable.
   *
   * @param node A JJT node holding a variable identifier
   * @return A new DAGVariable holding the variable's descriptor.
   */
  private DAGVariable buildVariable(SimpleNode node) {
    assert node.is(JJTIDENTIFIER);

    String varName = node.jjtGetVal();
    VariableDescriptor var = locals.resolve(varName);

    // ERROR: $variablename cannot be resolved to a variable.
    if (var == null) {
      System.err.println(varName + " cannot be resolved to a variable");
      status(MINOR_ERRORS);

      // TODO: Handle UnresolvedVariableName error continuation
      return null;
    }

    return new DAGVariable(var);
  }
}
