package compiler.dag;

import static jjt.jmmTreeConstants.*;
import static compiler.symbols.PrimitiveDescriptor.*;
import static compiler.symbols.TypeDescriptor.typematch;

import java.util.HashMap;

import compiler.symbols.FunctionSignature;
import compiler.symbols.ResolverClass.Deduction;
import compiler.exceptions.InternalCompilerError;
import compiler.modules.CompilationStatus;
import compiler.symbols.*;

import jjt.SimpleNode;

/**
 * A Factory class that manages the construction of one DAGExpression when a JJT
 * Expression is found in the Abstract Syntax Tree. It knows how to instantiate
 * all the possible subclasses of DAGExpression for each possible type of AST node.
 *
 * If more Expressions are found within the initial JJT Expression (naturally) no more are created.
 */
public class ExpressionFactory extends BaseDAGFactory {
  /**
   * Set of DAG expressions already constructed, for common subexpression elimination.
   */
  private final HashMap<DAGExpression, DAGExpression> cache = new HashMap<>();

  private final ExpressionTransformer transformer;

  /**
   * @param locals The table of locals variables.
   */
  ExpressionFactory(FunctionLocals locals) {
    super(locals);
    this.transformer = new ExpressionOptimizer(this);
  }

  public DAGExpression build(SimpleNode node, CompilationStatus tracker, TypeDescriptor type) {
    assert tracker != null;
    DAGExpression expression = build(node, type);
    tracker.update(status());
    return expression;
  }

  public DAGExpression build(SimpleNode node, TypeDescriptor type) {
    DAGExpression expression = build(node);
    return assertType(expression, type, node);
  }

  @Override
  public DAGExpression build(SimpleNode node, CompilationStatus tracker) {
    assert tracker != null;
    DAGExpression expression = build(node);
    tracker.update(status());
    return expression;
  }

  /**
   * Construct a new DAGExpression for this SimpleNode. It is possible for an equivalent
   * DAGExpression to exist in the expression cache.
   *
   * @param node The AST's SimpleNode object.
   * @return The DAGExpression node.
   */
  @Override
  public DAGExpression build(SimpleNode node) {
    // ... common pre-build

    // Forward the build to the appropriate build function.
    switch (node.getId()) {
    case JJTINTEGER:
      return reuse(buildInteger(node));
    case JJTTRUE:
    case JJTFALSE:
      return reuse(buildBoolean(node));
    case JJTIDENTIFIER:
      return reuse(buildVariable(node));
    case JJTTHIS:
      return reuse(buildThis(node));
    case JJTNEWINTARRAY:
      return reuse(buildNewIntArray(node));
    case JJTNEWCLASS:
      return reuse(buildNewClass(node));
    case JJTLENGTH:
      return reuse(buildLength(node));
    case JJTNOT:
      return reuse(buildUnaryOp(node));
    case JJTAND:
    case JJTLT:
    case JJTSUM:
    case JJTSUB:
    case JJTMUL:
    case JJTDIV:
      return reuse(buildBinaryOp(node));
    case JJTBRACKET:
      return reuse(buildBracket(node));
    case JJTCALL:
      return reuse(buildCall(node));
    }

    throw new InternalCompilerError();
  }

  private DAGExpression reuse(DAGExpression dagNode) {
    dagNode = transformer.optimize(dagNode);

    if (cache.containsKey(dagNode)) {
      return cache.get(dagNode);
    } else {
      cache.put(dagNode, dagNode);
      return dagNode;
    }
  }

  /**
   * @param node A JJT node holding an integer literal constant
   * @return A new DAGIntegerConstant holding the constant.
   */
  private DAGIntegerConstant buildInteger(SimpleNode node) {
    assert node.is(JJTINTEGER);

    String intString = node.jjtGetVal();

    try {
      int constant = Integer.parseInt(intString);
      return new DAGIntegerConstant(constant);
    }
    // ERROR: Integer literal constant value is not representable.
    catch (NumberFormatException e) {
      System.err.println("The literal " + intString + " of type int is out of range");
      update(Codes.MINOR_ERRORS);
      return new DAGIntegerConstant(1);
    }
  }

  /**
   * @param node A JJT node holding a true or false boolean literal
   * @return A new DAGBooleanConstant holding the constant.
   */
  private DAGBooleanConstant buildBoolean(SimpleNode node) {
    assert node.is(JJTTRUE) || node.is(JJTFALSE);

    if (node.is(JJTTRUE)) {
      return new DAGBooleanConstant(true);
    } else {
      return new DAGBooleanConstant(false);
    }
  }

  /**
   * @param node A JJT node holding a variable identifier
   * @return A new DAGVariable holding the variable's descriptor.
   */
  private DAGVariable buildVariable(SimpleNode node) {
    assert node.is(JJTIDENTIFIER);

    String varName = node.jjtGetVal();
    VariableDescriptor var = locals.resolve(varName);

    // ERROR: varName cannot be resolved to a variable.
    if (var == null) {
      System.err.println(varName + " cannot be resolved to a variable");
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

  /**
   * @SemanticError: Cannot use this in a static context.
   *
   * @param node A JJT node holding a this literal keyword
   * @return A new DAGThis node.
   */
  private DAGThis buildThis(SimpleNode node) {
    assert node.is(JJTTHIS);

    ThisDescriptor thisVar = locals.getThis();

    // ERROR: Cannot use this in a static context.
    if (thisVar == null) {
      System.err.println("Cannot use this in a static context");
      update(Codes.MAJOR_ERRORS);
      return null;
    }

    return new DAGThis(thisVar);
  }

  /**
   * @SemanticError: Type mismatch: expected int, but found X.
   *
   * @param node A JJT node representing a new int array declaration.
   * @return A new DAGNewIntArray node.
   */
  private DAGNewIntArray buildNewIntArray(SimpleNode node) {
    assert node.is(JJTNEWINTARRAY);

    SimpleNode indexExpressionNode = node.jjtGetChild(0);
    DAGExpression expression = reuse(build(indexExpressionNode));

    expression = assertType(expression, intDescriptor, indexExpressionNode);

    /*
    // ERROR: Type mismatch: expected int, but found X.
    if (!typematch(expression.getType(), intDescriptor)) {
      System.err.println("Type mismatch: expected int type, but found " + expression.getType());
      update(Codes.MINOR_ERRORS);
    }
    */

    return new DAGNewIntArray(expression);
  }

  /**
   * No semantic errors.
   *
   * @param node A JJT node representing a new declaration for a class type, with no args.
   * @return A new DAGNewClass node.
   */
  private DAGNewClass buildNewClass(SimpleNode node) {
    assert node.is(JJTNEWCLASS);

    SimpleNode classTypeNode = node.jjtGetChild(0);
    assert classTypeNode.is(JJTCLASSTYPE);

    String className = classTypeNode.jjtGetVal();
    ClassDescriptor classDescriptor = (ClassDescriptor) TypeDescriptor.getOrCreate(className);

    return new DAGNewClass(classDescriptor);
  }

  /**
   * @SemanticError: Type mismatch: expected int[], but found X.
   *
   * @param node A JJT node representing access to a property called 'length' of an int array.
   * @return A new DAGLength node.
   */
  private DAGLength buildLength(SimpleNode node) {
    assert node.is(JJTLENGTH);

    SimpleNode expressionNode = node.jjtGetChild(0);
    DAGExpression expression = reuse(build(expressionNode));

    expression = assertType(expression, intArrayDescriptor, expressionNode);

    // ERROR: Type mismatch: expected int[], but found X.
    if (!typematch(expression.getType(), intArrayDescriptor)) {
      System.err.println("Type mismatch: expected int[] type, but found " + expression.getType());
      update(Codes.MINOR_ERRORS);
    }

    return new DAGLength(expression);
  }

  /**
   * @SemanticError: Type mismatch: expected boolean, but found X.
   *
   * @param node A JJT node representing a unary operation. Only ! is supported.
   * @return A new DAGNot node.
   */
  private DAGNot buildUnaryOp(SimpleNode node) {
    assert node.is(JJTNOT);

    SimpleNode expressionNode = node.jjtGetChild(0);
    DAGExpression expression = build(expressionNode);

    expression = assertType(expression, booleanDescriptor, expressionNode);

    /*
    // ERROR: Type mismatch: expected boolean, but found X.
    if (!typematch(expression.getType(), booleanDescriptor)) {
      System.err.println("Type mismatch: expected boolean type, but found " + expression.getType());
      update(Codes.MINOR_ERRORS);
    }
    */

    return new DAGNot(expression);
  }

  /**
   * @SemanticError: Type mismatch: expected [int, boolean], but found X.
   *
   * @param node A JJT node representing a binary operation. Only ! is supported.
   * @return A new DAGBinaryOp node, if not optimized.
   */
  private DAGExpression buildBinaryOp(SimpleNode node) {
    assert node.is(JJTAND) || node.is(JJTLT) || node.is(JJTSUM) || node.is(JJTSUB)
        || node.is(JJTMUL) || node.is(JJTDIV);

    SimpleNode lhsNode = node.jjtGetChild(0);
    SimpleNode rhsNode = node.jjtGetChild(1);

    DAGExpression lhs = reuse(build(lhsNode));
    DAGExpression rhs = reuse(build(rhsNode));
    BinaryOperator op = BinaryOperator.from(node);

    lhs = assertType(lhs, op.getOperandType(), lhsNode);
    rhs = assertType(rhs, op.getOperandType(), rhsNode);

    /*
    // ERROR: Type mismatch in the lhs.
    if (!typematch(lhs.getType(), op.getOperandType())) {
      System.err.println("Type mismatch: expected " + op.getOperandType() + " type, but found "
                         + lhs.getType());
      update(Codes.MINOR_ERRORS);
    }

    // ERROR: Type mismatch in the rhs.
    if (!typematch(rhs.getType(), op.getOperandType())) {
      System.err.println("Type mismatch: expected " + op.getOperandType() + " type, but found "
                         + rhs.getType());
      update(Codes.MINOR_ERRORS);
    }
    */

    return new DAGBinaryOp(op, lhs, rhs);
  }

  /**
   * @SemanticError: Type mismatch: Expected int[] type, but found X.
   * @SemanticError: Type mismatch: Expected int type for bracket index, but found X.
   *
   * @param node A JJT Bracket node representing an array access.
   * @return A possibly reused DAGBracket node.
   */
  private DAGBracket buildBracket(SimpleNode node) {
    assert node.is(JJTBRACKET);

    SimpleNode arrayNode = node.jjtGetChild(0);
    SimpleNode indexNode = node.jjtGetChild(1);

    DAGExpression array = reuse(build(arrayNode));
    DAGExpression index = reuse(build(indexNode));

    array = assertType(array, intArrayDescriptor, arrayNode);
    index = assertType(index, intDescriptor, indexNode);

    /*
    // ERROR: Type mismatch in the array expression.
    if (!typematch(intArrayDescriptor, array.getType())) {
      System.err.println("Type mismatch: expected int[] type, but found " + array.getType());
      update(Codes.MINOR_ERRORS);
    }

    // ERROR: Type mismatch in the index expression.
    if (!typematch(intDescriptor, index.getType())) {
      System.err.println("Type mismatch: expected int type, but found " + index.getType());
      update(Codes.MINOR_ERRORS);
    }
    */

    return new DAGBracket(array, index);
  }

  /**
   * @SemanticError: Expression E is not an object.
   * @SemanticError: The method M is undefined for the type T.
   * @SemanticError: No overload of method M for type T matches the signature S.
   *
   * @param node A JJT function call node, either member or static method.
   * @return A new DAGCall node.
   */
  private DAGCall buildCall(SimpleNode node) {
    assert node.is(JJTCALL);

    // * Need to differentiate between static and non-static method calls.
    // If the objectNode is a JJT Identifier node, it might be the name
    // of a variable or the name of a class. Variables take precedence.
    // A class name is otherwise assumed.

    SimpleNode objectNode = node.jjtGetChild(0);

    if (objectNode.is(JJTIDENTIFIER)) {
      String identifier = objectNode.jjtGetVal();

      // Failed to resolve variable name.
      if (locals.resolve(identifier) == null) {
        return buildStaticCall(node);
      }
    }

    return buildMethodCall(node);
  }

  /**
   * @SemanticError: The method M is undefined for the type T.
   * @SemanticError: No overload of method M for type T matches the signature S.
   *
   * @param node A JJT static method call node.
   * @return A new DAGStaticCall node.
   */
  private DAGStaticCall buildStaticCall(SimpleNode node) {
    assert node.is(JJTCALL);

    SimpleNode classNode = node.jjtGetChild(0);
    SimpleNode methodNameNode = node.jjtGetChild(1);
    SimpleNode argumentListNode = node.jjtGetChild(2);
    assert classNode.is(JJTIDENTIFIER);
    assert methodNameNode.is(JJTMETHODNAME);
    assert argumentListNode.is(JJTARGUMENTLIST);

    String className = classNode.jjtGetVal();
    ClassDescriptor classDescriptor = (ClassDescriptor) TypeDescriptor.getOrCreate(className);

    String methodName = methodNameNode.jjtGetVal();

    int numArguments = argumentListNode.jjtGetNumChildren();
    DAGExpression[] arguments = new DAGExpression[numArguments];
    TypeDescriptor[] types = new TypeDescriptor[numArguments];

    for (int i = 0; i < numArguments; ++i) {
      arguments[i] = reuse(build(argumentListNode.jjtGetChild(i)));
      types[i] = arguments[i].getType();
    }

    FunctionSignature signature = new FunctionSignature(types);

    // * Write the most generic error possible.
    // ERROR #1: The method M is undefined for type T.
    if (!classDescriptor.hasStaticMethod(methodName)) {
      System.err.println("Static method " + methodName + " is undefined for class " + className);
      update(Codes.MAJOR_ERRORS);
    }
    // ERROR #2: No method M for type T matches the signature S.
    else if (!classDescriptor.hasStaticMethod(methodName, signature)) {
      System.err.println("No overload of static method " + methodName + " of class " + className
                         + " matches the signature " + methodName + signature);
      update(Codes.MAJOR_ERRORS);
    }
    // Attempt deduction.
    else {
      Deduction deduction = classDescriptor.deduceStatic(methodName, signature);
      assert deduction.wasSuccess();

      // Dispatch to return type overload resolution?
      if (!deduction.wereMultiple()) {
        CallableDescriptor callable = deduction.getCallable();
        DAGStaticCall call = new DAGStaticCall(methodName, signature, callable, arguments);
        assertArgumentList(call, node);
        return call;
      }
      // yes
    }

    return new DAGStaticCall(classDescriptor, methodName, signature, arguments);
  }

  /**
   * @SemanticError: Expression E is not an object.
   * @SemanticError: The method M is undefined for the type T.
   * @SemanticError: No overload of method M for type T matches the signature S.
   *
   * @param node A JJT member method call node.
   * @return A new DAGMethodCall node.
   */
  private DAGMethodCall buildMethodCall(SimpleNode node) {
    assert node.is(JJTCALL);

    SimpleNode objectNode = node.jjtGetChild(0);
    SimpleNode methodNameNode = node.jjtGetChild(1);
    SimpleNode argumentListNode = node.jjtGetChild(2);
    assert methodNameNode.is(JJTMETHODNAME);
    assert argumentListNode.is(JJTARGUMENTLIST);

    DAGExpression object = reuse(build(objectNode));

    String methodName = methodNameNode.jjtGetVal();

    int numArguments = argumentListNode.jjtGetNumChildren();
    DAGExpression[] arguments = new DAGExpression[numArguments];
    TypeDescriptor[] types = new TypeDescriptor[numArguments];

    for (int i = 0; i < numArguments; ++i) {
      arguments[i] = reuse(build(argumentListNode.jjtGetChild(i)));
      types[i] = arguments[i].getType();
    }

    FunctionSignature signature = new FunctionSignature(types);

    TypeDescriptor objectType = object.getType();

    // * Write the most generic error possible.
    // ERROR #1: Object expression is not of class type.
    if (objectType != null && !objectType.isClass()) {
      System.err.println("Expression used in method call is not of class type");
      update(Codes.MAJOR_ERRORS);
    } else if (objectType != null) {
      ClassDescriptor objectClass = (ClassDescriptor) objectType;
      String className = objectClass.getName();

      // ERROR #2: The method M is undefined for type T.
      if (!objectClass.hasMethod(methodName)) {
        System.err.println("Method " + methodName + " is undefined for class " + className);
        update(Codes.MAJOR_ERRORS);
      }
      // ERROR #3: No method M for type T matches the signature S.
      else if (!objectClass.hasMethod(methodName, signature)) {
        System.err.println("No overload of method " + methodName + " of class " + objectClass
                           + " matches the signature " + methodName + signature);
        update(Codes.MAJOR_ERRORS);
      }
      // Attempt deduction.
      else {
        Deduction deduction = objectClass.deduce(methodName, signature);
        assert deduction.wasSuccess();

        // Dispatch to return type overload resolution?
        if (!deduction.wereMultiple()) {
          CallableDescriptor callable = deduction.getCallable();
          DAGMethodCall call = new DAGMethodCall(object, methodName, signature, callable,
                                                 arguments);
          assertArgumentList(call, node);
          return call;
        } else {
        }
      }
    }

    return new DAGMethodCall(object, methodName, signature, arguments);
  }

  /**
   * * Analysis of the problem of function signature deduction
   * During the process of evaluating a function call in buildCall(), we might
   * encounter an invocation of a method whose class we do not know about:
   *
   *          SomeExternalClass obj1;
   *          ...
   *          ...
   *          ... ... obj1.method1(a1, a2, a3, a4) ... ...
   *                               |   |   |   ^--> int
   *                               |   |   +--> int
   *                               |   +--> MyClass
   *                               +--> boolean
   *
   * and even if we know (we should) the types of a1, a2, a3 and a4 perfectly, we cannot
   * deduce the return type of method1. This doesn't happen in a normal programming language which
   * does not allow for return type overloading. C11 and C++, for example, require formal function
   * declaration before the function is used, hence the return type is known upfront (solves the
   * chicken-and-egg problem). In Java its even simpler, it just looks for the class in the
   * class database, and verifies what return type this overload has -- it either exists or it
   * doesn't, there's no chicken-and-egg problem to begin with.
   *
   * Now, we can't just reject this code. We also can't just assume it returns void, if it is used
   * in e.g.
   *
   *          int a;
   *          a = obj1.method1(a1, a2, a3, a4);
   *
   * The specification says we must accept this code.
   *
   * It gets worse...
   *
   * An invocation of a method whose class we do know about (a JMMClassDescriptor, namely the
   * class we're compiling) but the arguments have unknown types (they are calls of functions
   * like the one above, or other functions like these ones recursively), we might not know which
   * overload we should select:
   *
   *          OurClass obj2;
   *          ...
   *          ...
   *          ... ... obj2.method2(b1, b2, b3, b4) ... ...
   *                               ^   ^   ^   ^--> ?
   *                               |   |   +--> int
   *                               |   +--> ?
   *                               +--> boolean
   *          And two possible overloads:
   *                public int  method2(boolean, int,   int, int[])
   *               public int[] method2(boolean, int[], int, SomeExternalClass)
   *
   * Since we cannot deduce the types of b2 and b4, we must disambiguate this call by looking at
   * the return type of method2(,,,), or at least the context where it is used. If the overload is
   * deduced, the types of b2 and b4 are as well.
   *
   * It gets worse.
   *
   * We could have the two situations above simultaneously:
   *
   *          SomeExternalClass obj1;
   *          ...
   *          ...
   *          ... ... obj1.method1(a1, a2, a3, a4) ... ...
   *                               |   |   |   ^--> int
   *                               |   |   +--> int
   *                               |   +--> ?
   *                               +--> boolean
   *
   * Now the type of a2 is not deduceable. It could be anything. It is a DAGCall, because only these
   * can have undetermined types. But we need to know the return type of a2 for Jasmin. So we must
   * assign a type to a2; this type will be assumed.
   *
   * There are a few simpler solutions to the problems than the ones we're going to pick:
   *      1. The easy path: just pick one overload at random, and continue
   *      2. Crash the compiler with an internal error
   *      3. Kindly ask the user to disambiguate by assigning b2 or b4 to a local variable.
   *      4. In the first situation, just assume it returns an int or void depending on context.
   *
   * We're going to do return type overload resolution. Given the return type of method2, we can
   * discard some overloads and hopefully conclude which function should be called.
   *
   * ! If multiple overloads are available even after overload resolution, an error is logged.
   * ! To avoid recursion and dark, hard to comprehend diagnostic messages,
   * ! this resolution is not going to be recursive (9 May 2019)
   *
   * This is something no normal programming language ever does. But JMM is not a normal programming
   * language: it has no type conversions (making this enterprise considerably easier) and
   * a very defficient specification: we must somehow write a compiler that respects the two
   * following requirements:
   *
   *      Allow invokation of functions on external, unknown classes with unknown parameter
   *         signatures and return type, while not performing semantic analysis on them.
   *      @ Section 2, Semantic Analysis, page 4/11
   *
   *      Write bytecode for Jasmin, where every function invokation must specify a complete
   *         method signature, including classname, parameter signature and return type.
   *      @ Jasmin Instruction Set Docs, http://jasmin.sourceforge.net/instructions.html
   *
   * Clearly these two requirements should not coexist on the same specification, but they do, so
   * this is our solution to this problem. Everywhere parameter types cannot be deduced, their
   * deduction is postponed for return type overload resolution. For external class function calls
   * with undeduceable argument (the last case) then the argument type trully cannot be resolved: it
   * is defaulted to X on method signatures, where X is never void, and can be set on
   * TypeDescriptor.unknownResolvedType. Check what it is there, it might change on the fly.
   * Furthermore, a warning diagnostic is issued.
   *
   * Top level function calls (aka statement calls, with no encapsulating expression or assignment)
   * are assumed to return void; this is handled in assertStatement(), and warrant warning
   * diagnostics as well.
   *
   * Everything else is handled in assertType(), where we provide the expected non-void return type.
   *
   * For example, if the method2 above is applied the bracket operator (DAGBracket) or the length
   * operator (DAGLength) we know we should pick the second overload, because it has to return an
   * int[]; if it is used in a binary operation (DAGBinaryOp), we should pick the first overload,
   * because it has to return an int.
   *
   * If method2 above can be deduced to return 'int', then the function called can be deduced, and
   * so can the types of b2 and b4 -- int and int[] respectively -- then assertType() is called
   * again on these arguments, recursively if necessary. Further assertType() calls on the same
   * DAGCall node do nothing at all.
   *
   * * Solutions
   * To summarize, we have six problems:
   *
   *  ? Problem #1: Expression(A) and statement(B) contexts
   *              Call external function with all known parameter and arguments types, but unknown
   *              return type.
   *  ? Problem #2: Expression(A) and statement(B) contexts
   *              Call some function with some unknown argument types, resulting in impossibility
   *              of overload resolution.
   *  ? Problem #3: Expression(A) and statement(B) contexts
   *              Both of the above, i.e. external function call with some unknown argument type.
   *
   * and six solutions:
   *
   *  ? Solution #1A (simple):
   *              Set the call's return type to the deduced one.
   *              ! This is required by the specification.
   *  ? Solution #1B (simple):
   *              Set the call's return type to void.
   *              ! This is left unspecified by the specification, but it must be accepted.
   *  ? Solution #2A (complex):
   *              Use the deduced return type to help with overload resolution.
   *              If multiple candidate functions still satisfy the invokation, it is an error.
   *              If no function matches in either case, it is an error.
   *              Exactly one overload must match after return type overload resolution.
   *              ! This is unmentioned in the specification, and does not have to be accepted.
   *  ? Solution #2B (very complex):
   *              Do approximately the following: #2A with return type void, if unsuccessful then do
   *              #2A with unspecified return type and do not assertType() the arguments.
   *              ! This is unmentioned in the specification, and does not have to be accepted.
   *  ? Solution #3A (simple):
   *              Set the call's return type to the deduced one. Do not assertType() any arguments.
   *              ! This is required by the specification.
   *  ? Solution #3B (simple):
   *              Set the call's return type to void. Do not assertType() any arguments.
   *              ! This is left unspecified by the specification, but it must be accepted.
   *
   * * Implementation of #2A and #2B
   * The auxiliary classes are in compiler.symbols.resolve and will not be documented. Their
   * interface and implementation may change without notice.
   */

  /**
   * Asserts that the given DAGExpression node is of the given type. The SimpleNode corresponding to
   * this DAGExpression node is given as well. If the DAGExpression node is a DAGCall, verify
   * whether return type overload resolution should be applied.
   *
   * @param expression The DAGExpression node whose type is being checked.
   * @param type       The type the node should have.
   * @param node       The SimpleNode used to construct the expression, for diagnostics.
   * @return The DAGExpression node.
   */
  public DAGExpression assertType(DAGExpression expression, TypeDescriptor type, SimpleNode node) {
    assert expression != null && node != null;
    if (type == null) return expression;

    if (expression instanceof DAGCall) {
      DAGCall call = (DAGCall) expression;

      if (!call.isDeduced()) {
        if (call.getCallClassUnchecked() instanceof JMMClassDescriptor) {
          deduceJMMCallType(call, type, node);
        } else if (call.getCallClassUnchecked() instanceof UnknownClassDescriptor) {
          deduceUnknownCallType(call, type, node);
        }
      }
    }

    if (expression.getType() != null && expression.getType() != type) {
      System.err.println("Type mismatch in expression: expected type " + type + ", but found "
                         + expression.getType());
      update(Codes.MAJOR_ERRORS);
    }

    return expression;
  }

  /**
   * Apply return type overload resolution for a DAGCall applied on a JMM class, member method or
   * static function. The overload resolution succeeds iff there is exactly one matching function.
   */
  private void deduceJMMCallType(DAGCall call, TypeDescriptor type, SimpleNode node) {
    assert !call.isDeduced() && call.getCallClass() instanceof JMMClassDescriptor;

    JMMClassDescriptor classDescriptor = (JMMClassDescriptor) call.getCallClass();
    FunctionSignature signature = call.getOriginalSignature();
    String methodName = call.getMethodName(), className = classDescriptor.getName();

    assert !call.getOriginalSignature().isComplete();
    assert classDescriptor.hasMethod(methodName, signature);

    call.setDeducedReturnType(type);

    Deduction deduction;

    if (call.isStatic()) {
      deduction = classDescriptor.deduceStatic(methodName, signature, type);
    } else {
      deduction = classDescriptor.deduce(methodName, signature, type);
    }

    if (!deduction.wasSuccess()) {
      System.err.println("Unsuccessful return type overload resolution:"
                         + " No overload of method " + methodName + " of class " + className
                         + " matches signature " + signature + " and returns " + type);
      update(Codes.MAJOR_ERRORS);
    } else if (deduction.wereMultiple()) {
      System.err.println("Unsuccessful return type overload resolution:"
                         + " Multiple overloads of method " + methodName + " of class " + className
                         + " match the signature " + methodName + signature + " and return " + type
                         + ". Please disambiguate your intended method call by assigning the result"
                         + " of external function call arguments to local variables, and using"
                         + " those locals variables as arguments to this function call instead.");
      update(Codes.MAJOR_ERRORS);
    } else {
      // Moderate Warning
      System.out.println("Warning: Resolved overload of method " + methodName + " of class "
                         + className + " matching signature " + methodName + signature
                         + " and returning " + type + " to method: " + deduction.getCallable());
      call.setDeducedCallable(deduction.getCallable(), type);
      assertArgumentList(call, node);
    }
  }

  /**
   * Apply return type overload resolution for a DAGCall applied on a Java class, member method or
   * static function. The overload resolution succeeds, and sets the return type of the function,
   * which will be necessary for code generation.
   */
  private void deduceUnknownCallType(DAGCall call, TypeDescriptor type, SimpleNode node) {
    assert !call.isDeduced() && call.getCallClass() instanceof UnknownClassDescriptor;

    assert call.getCallable() instanceof JavaCallableDescriptor;
    ((JavaCallableDescriptor) call.getCallable()).deduceReturnType(type);
    call.setDeducedReturnType(type);
    assertArgumentList(call, node);
  }

  /**
   * Asserts that the given DAGExpression node is a top-level statement. The SimpleNode
   * corresponding to this DAGExpression node is given as well. If the DAGExpression node is a
   * DAGCall, verify whether return type overload resolution should be applied.
   *
   * @param expression The DAGExpression node whose type is being verified.
   * @param node       The SimpleNode used to construct the expression, for diagnostics.
   * @return The DAGExpression node.
   */
  public DAGExpression assertStatement(DAGExpression expression, SimpleNode node) {
    assert expression != null && node != null;

    if (expression instanceof DAGCall) {
      DAGCall call = (DAGCall) expression;

      if (!call.isDeduced()) {
        if (call.getCallClass() instanceof JMMClassDescriptor) {
          assertJMMCallStatement(call, node);
        } else if (call.getCallClass() instanceof UnknownClassDescriptor) {
          assertUnknownCallStatement(call, node);
        }
      }
    }

    return expression;
  }

  /**
   * Apply return type overload resolution for a DAGCall applied on a JMM class, member method or
   * static function. The overload resolution succeeds iff there is exactly one matching function.
   * In this case, the resolution failed because at least two methods matched the signature; but if
   * the node is a top level statement there will be no further return type deduction, so void must
   * be assumed. If there are no void methods
   */
  private void assertJMMCallStatement(DAGCall call, SimpleNode node) {
    assert !call.isDeduced() && call.getCallClass() instanceof JMMClassDescriptor;

    JMMClassDescriptor classDescriptor = (JMMClassDescriptor) call.getCallClass();
    FunctionSignature signature = call.getOriginalSignature();
    String methodName = call.getMethodName(), className = classDescriptor.getName();

    assert !call.getOriginalSignature().isComplete();
    assert classDescriptor.hasMethod(methodName, signature);

    call.setDeducedReturnType(voidDescriptor);

    Deduction deduction;

    if (call.isStatic()) {
      deduction = classDescriptor.deduceStatic(methodName, signature, voidDescriptor);
    } else {
      deduction = classDescriptor.deduce(methodName, signature, voidDescriptor);
    }

    if (deduction.wereMultiple()) {
      System.err.println("Unsuccessful return type overload resolution:"
                         + " Multiple overloads of method " + methodName + " of class " + className
                         + " match the signature " + methodName + signature + " and return void."
                         + " Please disambiguate your intended method call by assigning the result"
                         + " of external function call arguments to local variables, and using"
                         + " those locals variables as arguments to the function call instead.");
      update(Codes.MAJOR_ERRORS);
    } else if (!deduction.wasSuccess()) {
      System.err.println("Unsuccessful return type overload resolution:"
                         + " Multiple overloads of method " + methodName + " of class " + className
                         + " match the signature " + methodName + signature
                         + " and return non-void, but none return void."
                         + " Please disambiguate your intended method call by "
                         + " assigning the result of external function call arguments to local"
                         + " variables, and using those locals variables as arguments to this"
                         + " function call instead.");
      update(Codes.MAJOR_ERRORS);
    } else {
      // Severe Warning
      System.out.println("Warning: Resolved overload of method " + methodName + " of class "
                         + className + " matching signature " + methodName + signature
                         + " and being used as a statement to method: " + deduction.getCallable()
                         + " because it returns void.");

      call.setDeducedCallable(deduction.getCallable(), voidDescriptor);
      assertArgumentList(call, node);
    }
  }

  /**
   * Apply return type overload resolution for a DAGCall applied on a Java class, member method or
   * static function. The overload resolution succeeds, and sets the return type of the function,
   * which will be necessary for code generation.
   */
  private void assertUnknownCallStatement(DAGCall call, SimpleNode node) {
    assert !call.isDeduced() && call.getCallClass() instanceof UnknownClassDescriptor;

    String className = call.getCallClass().getName(), methodName = call.getMethodName();

    // Severe Warning
    System.out.println("Warning: Resolved method " + methodName + " of unknown class " + className
                       + " matching signature " + methodName + call.getOriginalSignature()
                       + " and being used as a statement top-level statement to method: "
                       + call.getCallable() + ", which returns void.");

    assert call.getCallable() instanceof JavaCallableDescriptor;
    ((JavaCallableDescriptor) call.getCallable()).deduceReturnType(voidDescriptor);
    call.setDeducedReturnType(voidDescriptor);
    assertArgumentList(call, node);
  }

  /**
   * Asserts the type of each argument in an argument list.
   */
  private void assertArgumentList(DAGCall call, SimpleNode node) {
    for (int i = 0; i < call.getNumArguments(); ++i) {
      DAGExpression argument = call.getArgument(i);
      TypeDescriptor argType = call.getCallable().getParameterType(i);
      SimpleNode argNode = node.jjtGetChild(2).jjtGetChild(i);
      call.setArgument(i, assertType(argument, argType, argNode));
    }
  }
}
