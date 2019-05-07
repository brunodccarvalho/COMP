# Decisions

Use trueBranch / falseBranch or thenBody / elseBody?

Use block depth counter in DAGNode?

Use forward pointer(s) in DAGNode?

Use back pointer(s) in DAGNode?

Add an assertType(expected) method to DAGExpression and DAGAssignment
that will autodeduce the expected value of the expression or value in question,
in case it is not known.

In NodeFactory, wrap nodes[i] = build(statementNode) in try catch, remove CompilerModule.
