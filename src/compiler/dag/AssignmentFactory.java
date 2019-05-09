package compiler.dag;

import static jjt.jmmTreeConstants.*;

import static compiler.symbols.PrimitiveDescriptor.*;
import static compiler.symbols.TypeDescriptor.typematch;

import compiler.modules.CompilationStatus;
import compiler.modules.DiagnosticsHandler;
import compiler.symbols.FunctionLocals;
import compiler.symbols.LocalDescriptor;
import compiler.symbols.MemberDescriptor;
import compiler.symbols.ParameterDescriptor;
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
  private final ExpressionFactory factory;

  AssignmentFactory(FunctionLocals locals) {
    super(locals);
    this.factory = new ExpressionFactory(locals);
  }

  @Override
  public DAGAssignment build(SimpleNode assignmentNode, CompilationStatus tracker) {
    assert tracker != null;
    DAGAssignment assignment = build(assignmentNode);
    tracker.update(status());
    return assignment;
  }

  /**
   * Construct a new DAGAssignment for this SimpleNode. It is possible for this node to be
   * a simple assignment node (variable = Expression) or a bracket assignment (variable[Expression]
   * = expression). DAGAssignments cannot be reused.
   *
   * @param assignmentNode The AST's SimpleNode object representing an assignment.
   * @return The DAGExpression node.
   */
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

  /**
   * @SemanticError: Type mismatch: expected type T, but expression has type E.
   *
   * @param assignmentNode A JJT node representing a variable assignment.
   * @return A new DAGAssignment, holding the variable and the expression.
   */
  private DAGAssignment buildAssignment(SimpleNode node) {
    assert node.is(JJTASSIGNMENT);

    SimpleNode variableNode = node.jjtGetChild(0);
    SimpleNode expressionNode = node.jjtGetChild(1);
    assert variableNode.is(JJTIDENTIFIER);

    DAGVariable variable = buildVariable(variableNode);
    DAGExpression expression = factory.build(expressionNode, variable.getType());

    /*
    // Error: Type mismatch: Expected type T, but expression has type E.
    if (!typematch(variable.getType(), expression.getType())) {
      DiagnosticsHandler.typeMismatch(node, variable.getType(), expression.getType());
      update(Codes.MAJOR_ERRORS);
    }
    */

    return new DAGAssignment(variable, expression);
  }

  /**
   * @SemanticError: Type mismatch: expected type int, but expression has type E.
   *
   * @param assignmentNode A JJT node representing an array assignment.
   * @return A new DAGBracketAssignment, holding the variable and the expression.
   */
  private DAGBracketAssignment buildBracketAssignment(SimpleNode node) {
    assert node.is(JJTASSIGNMENT);

    SimpleNode bracketsNode = node.jjtGetChild(0);
    SimpleNode assignedExpressionNode = node.jjtGetChild(1);
    assert bracketsNode.is(JJTBRACKET);

    SimpleNode variableNode = bracketsNode.jjtGetChild(0);
    SimpleNode indexExpressionNode = bracketsNode.jjtGetChild(1);
    assert variableNode.is(JJTIDENTIFIER);

    DAGVariable var = buildVariable(variableNode);
    DAGExpression indexExpression = factory.build(indexExpressionNode, intDescriptor);
    DAGExpression assignedExpression = factory.build(assignedExpressionNode, intDescriptor);

    /*
    // ERROR: Type mismatch in the assigned expression.
    if (!typematch(intDescriptor, assignedExpression.getType())) {
      DiagnosticsHandler.typeMismatch(node, intDescriptor, assignedExpression.getType());
      update(Codes.MAJOR_ERRORS);
    }

    // ERROR: Type mismatch in the index expression.
    if (!typematch(intDescriptor, indexExpression.getType())) {
      DiagnosticsHandler.typeMismatch(indexExpressionNode, intDescriptor,
                                      assignedExpression.getType());
      update(Codes.MAJOR_ERRORS);
    }
    */

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

    // ERROR: varName cannot be resolved to a variable.
    if (var == null) {
      DiagnosticsHandler.unresolvedVarName(node, varName);
      update(Codes.MAJOR_ERRORS);
      return new DAGVariable();
    } else if (var instanceof LocalDescriptor) {
      return new DAGLocal((LocalDescriptor) var);
    } else if (var instanceof ParameterDescriptor) {
      return new DAGParameter((ParameterDescriptor) var);
    } else {
      return new DAGMember((MemberDescriptor) var);
    }
  }
}
