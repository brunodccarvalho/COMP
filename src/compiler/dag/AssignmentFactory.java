package compiler.dag;

import static jjt.jmmTreeConstants.*;
import compiler.symbols.FunctionLocals;
import jjt.SimpleNode;

/**
 * A Factory class that manages the construction of one DAGAssignment when a JJT
 * Assignment is found in the Abstract Syntax Tree. It knows how to instantiate
 * both DAGAssignment and DAGBracketAssignment subclasses.
 *
 * Two assignments are always different.
 */
public class AssignmentFactory extends CompilerModule {
    FunctionLocals locals;
    ExpressionFactory factory;

    AssignmentFactory(FunctionLocals locals){
        assert locals != null && node != null;
        this.locals = locals;
        this.factory = new ExpressionFactory(locals);
    }


    public DAGAssignment build(SimpleNode assignmentNode){
        assert assignmentNode.is(JJTASSIGNMENT);
    }

    private DAGAssignment buildAssignment(SimpleNode assignmentNode){
        
        DAGVariable var = this.buildVariable(assignmentNode.jjtGetChild(0));
        DAGExpression expression = this.factory.build(assignmentNode.jjtGetChild(2));
        return new DAGAssignment(var,expression);
    }

    private DAGBracketAssignment buildBracketAssignment(SimpleNode assignmentNode){
        DAGVariable var = this.buildVariable(assignmentNode.jjtGetChild(0));
        DAGExpression indexExpression = this.factory.build(assignmentNode.jjtGetChild(2));
        DAGExpression assignedExpression = this.factory.build(assignmentNode.jjtGetChild(5));
        return new DAGBracketAssignment(var,assignedExpression,indexExpression);
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

    private boolean compatibleTypes(DAGVariable var, DAGExpression expression){
        return expression.getType().equals(var.getType());
    }
}