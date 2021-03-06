package compiler.dag;

import static compiler.symbols.PrimitiveDescriptor.booleanDescriptor;
import static compiler.symbols.PrimitiveDescriptor.intArrayDescriptor;
import static compiler.symbols.PrimitiveDescriptor.intDescriptor;
import static compiler.symbols.TypeDescriptor.typematch;
import static compiler.symbols.TypeDescriptor.voidDescriptor;
import static jjt.jmmTreeConstants.JJTAND;
import static jjt.jmmTreeConstants.JJTARGUMENTLIST;
import static jjt.jmmTreeConstants.JJTBRACKET;
import static jjt.jmmTreeConstants.JJTCALL;
import static jjt.jmmTreeConstants.JJTCLASSTYPE;
import static jjt.jmmTreeConstants.JJTDIV;
import static jjt.jmmTreeConstants.JJTFALSE;
import static jjt.jmmTreeConstants.JJTIDENTIFIER;
import static jjt.jmmTreeConstants.JJTINTEGER;
import static jjt.jmmTreeConstants.JJTLENGTH;
import static jjt.jmmTreeConstants.JJTLT;
import static jjt.jmmTreeConstants.JJTMETHODNAME;
import static jjt.jmmTreeConstants.JJTMUL;
import static jjt.jmmTreeConstants.JJTNEWCLASS;
import static jjt.jmmTreeConstants.JJTNEWINTARRAY;
import static jjt.jmmTreeConstants.JJTNOT;
import static jjt.jmmTreeConstants.JJTSUB;
import static jjt.jmmTreeConstants.JJTSUM;
import static jjt.jmmTreeConstants.JJTTHIS;
import static jjt.jmmTreeConstants.JJTTRUE;

import compiler.exceptions.InternalCompilerError;
import compiler.modules.CompilationStatus;
import compiler.modules.DiagnosticsHandler;
import compiler.symbols.CallableDescriptor;
import compiler.symbols.ClassDescriptor;
import compiler.symbols.FunctionLocals;
import compiler.symbols.FunctionSignature;
import compiler.symbols.JavaCallableDescriptor;
import compiler.symbols.LocalDescriptor;
import compiler.symbols.MemberDescriptor;
import compiler.symbols.ParameterDescriptor;
import compiler.symbols.ResolverClass.Deduction;
import compiler.symbols.ThisDescriptor;
import compiler.symbols.TypeDescriptor;
import compiler.symbols.VariableDescriptor;
import jjt.SimpleNode;

/**
 * A Factory class that manages the construction of one DAGExpression when a JJT
 * Expression is found in the Abstract Syntax Tree. It knows how to instantiate
 * all the possible subclasses of DAGExpression for each possible type of AST node.
 *
 * If more Expressions are found within the initial JJT Expression (naturally) no more are created.
 */
public class ExpressionFactory extends BaseDAGFactory {
  private final ExpressionTransformer transformer;

  /**
   * @param locals The table of locals variables.
   */
  ExpressionFactory(FunctionLocals locals, CompilationStatus tracker) {
    super(locals, tracker);
    this.transformer = new ExpressionOptimizer(this);
  }

  /**
   * Build method #3: Transform a top-level statement expression node into a DAGExpression.
   *
   * @param node The AST's SimpleNode object.
   * @return The DAGExpression node.
   */
  public DAGExpression buildStatement(SimpleNode node) {
    DAGExpression expression = build(node);
    assertStatement(expression, node);
    return expression;
  }

  /**
   * Build method #2: Transform node into a DAGExpression of the given type.
   *
   *  ! Required type for the returned DAGExpression
   *
   * @param node The AST's SimpleNode object.
   * @param type The expected type of the returned DAGExpression.
   * @return The DAGExpression node.
   */
  public DAGExpression build(SimpleNode node, TypeDescriptor type) {
    DAGExpression expression = build(node);
    assertType(expression, type, node);
    return expression;
  }

  /**
   * Build method #1: Plainly transform node into a DAGExpression.
   *
   *  * No required type for the returned DAGExpression
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
      return transformer.optimize(buildInteger(node));
    case JJTTRUE:
    case JJTFALSE:
      return transformer.optimize(buildBoolean(node));
    case JJTIDENTIFIER:
      return transformer.optimize(buildVariable(node));
    case JJTTHIS:
      return transformer.optimize(buildThis(node));
    case JJTNEWINTARRAY:
      return transformer.optimize(buildNewIntArray(node));
    case JJTNEWCLASS:
      return transformer.optimize(buildNewClass(node));
    case JJTLENGTH:
      return transformer.optimize(buildLength(node));
    case JJTNOT:
      return transformer.optimize(buildUnaryOp(node));
    case JJTAND:
    case JJTLT:
    case JJTSUM:
    case JJTSUB:
    case JJTMUL:
    case JJTDIV:
      return transformer.optimize(buildBinaryOp(node));
    case JJTBRACKET:
      return transformer.optimize(buildBracket(node));
    case JJTCALL:
      return transformer.optimize(buildCall(node));
    }

    throw new InternalCompilerError();
  }

  /**
   * @param node A JJT node holding an integer literal constant
   * @return A DAGIntegerConstant holding the constant.
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
      DiagnosticsHandler.intLiteralOutOfRange(node, intString);
      update(Codes.MINOR_ERRORS);
      return new DAGIntegerConstant(1);
    }
  }

  /**
   * @param node A JJT node holding a true or false boolean literal
   * @return A DAGBooleanConstant holding the constant.
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
   * @return A DAGVariable holding the variable's descriptor, or a dummy for propagation.
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

  /**
   * @param node A JJT node holding a this literal keyword
   * @return A DAGThis node, or dummy for propagation.
   */
  private DAGVariable buildThis(SimpleNode node) {
    assert node.is(JJTTHIS);

    ThisDescriptor thisVar = locals.getThis();

    // ERROR: Cannot use this in a static context.
    if (thisVar == null) {
      DiagnosticsHandler.staticUseOfThis(node);
      update(Codes.MAJOR_ERRORS);
      return new DAGVariable();
    }

    return new DAGThis(thisVar);
  }

  /**
   * @param node A JJT node representing a new int array declaration.
   * @return A DAGNewIntArray node.
   */
  private DAGNewIntArray buildNewIntArray(SimpleNode node) {
    assert node.is(JJTNEWINTARRAY);

    SimpleNode indexExpressionNode = node.jjtGetChild(0);
    DAGExpression expression = build(indexExpressionNode);

    assertType(expression, intDescriptor, indexExpressionNode);

    return new DAGNewIntArray(expression);
  }

  /**
   * @param node A JJT node representing a new declaration for a class type, with no args.
   * @return A DAGNewClass node.
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
   * @param node A JJT node representing access to a property called 'length' of an int array.
   * @return A DAGLength node.
   */
  private DAGLength buildLength(SimpleNode node) {
    assert node.is(JJTLENGTH);

    SimpleNode expressionNode = node.jjtGetChild(0);
    DAGExpression expression = build(expressionNode);

    assertType(expression, intArrayDescriptor, expressionNode);

    return new DAGLength(expression);
  }

  /**
   * @param node A JJT node representing a unary operation. Only ! is supported.
   * @return A DAGNot node.
   */
  private DAGNot buildUnaryOp(SimpleNode node) {
    assert node.is(JJTNOT);

    SimpleNode expressionNode = node.jjtGetChild(0);
    DAGExpression expression = build(expressionNode);

    assertType(expression, booleanDescriptor, expressionNode);

    return new DAGNot(expression);
  }

  /**
   * @param node A JJT node representing a binary operation. Only ! is supported.
   * @return A DAGBinaryOp node, if not optimized.
   */
  private DAGExpression buildBinaryOp(SimpleNode node) {
    assert node.is(JJTAND) || node.is(JJTLT) || node.is(JJTSUM) || node.is(JJTSUB)
        || node.is(JJTMUL) || node.is(JJTDIV);

    SimpleNode lhsNode = node.jjtGetChild(0);
    SimpleNode rhsNode = node.jjtGetChild(1);

    DAGExpression lhsExpression = build(lhsNode);
    DAGExpression rhsExpression = build(rhsNode);
    BinaryOperator op = BinaryOperator.from(node);

    assertType(lhsExpression, op.getOperandType(), lhsNode);
    assertType(rhsExpression, op.getOperandType(), rhsNode);

    return new DAGBinaryOp(op, lhsExpression, rhsExpression);
  }

  /**
   * @param node A JJT Bracket node representing an array access.
   * @return A DAGBracket node.
   */
  private DAGBracket buildBracket(SimpleNode node) {
    assert node.is(JJTBRACKET);

    SimpleNode arrayNode = node.jjtGetChild(0);
    SimpleNode indexNode = node.jjtGetChild(1);

    DAGExpression arrayExpression = build(arrayNode);
    DAGExpression indexExpression = build(indexNode);

    assertType(arrayExpression, intArrayDescriptor, arrayNode);
    assertType(indexExpression, intDescriptor, indexNode);

    return new DAGBracket(arrayExpression, indexExpression);
  }

  /**
   * @param node A JJT function call node, either member or static method.
   * @return A DAGCall node.
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
   * @param node A JJT static method call node.
   * @return A DAGStaticCall node.
   */
  private DAGStaticCall buildStaticCall(SimpleNode node) {
    assert node.is(JJTCALL);

    SimpleNode classNode = node.jjtGetChild(0);
    SimpleNode staticNameNode = node.jjtGetChild(1);
    SimpleNode argumentListNode = node.jjtGetChild(2);
    assert classNode.is(JJTIDENTIFIER);
    assert staticNameNode.is(JJTMETHODNAME);
    assert argumentListNode.is(JJTARGUMENTLIST);

    String className = classNode.jjtGetVal();
    ClassDescriptor classDescriptor = (ClassDescriptor) TypeDescriptor.getOrCreate(className);

    String staticName = staticNameNode.jjtGetVal();

    int numArguments = argumentListNode.jjtGetNumChildren();
    DAGExpression[] arguments = new DAGExpression[numArguments];
    TypeDescriptor[] types = new TypeDescriptor[numArguments];

    for (int i = 0; i < numArguments; ++i) {
      arguments[i] = build(argumentListNode.jjtGetChild(i));
      types[i] = arguments[i].getType();
    }

    FunctionSignature signature = new FunctionSignature(types);

    // * Write the most generic error possible.
    // ERROR #1: The method M is undefined for type T.
    if (!classDescriptor.hasStaticMethod(staticName)) {
      DiagnosticsHandler.noSuchStaticMethod(node, staticName, className);
      update(Codes.MAJOR_ERRORS);
    }
    // ERROR #2: No method M for type T matches the signature S.
    else if (!classDescriptor.hasStaticMethod(staticName, signature)) {
      DiagnosticsHandler.noOverloadStatic(node, staticName, signature, className);
      update(Codes.MAJOR_ERRORS);
    }
    // Attempt deduction.
    else {
      Deduction deduction = classDescriptor.deduceStatic(staticName, signature);

      // ERROR #4: Function is external, and some parameter has unknown type
      if (!deduction.wasSuccess()) {
        for (int i = 0; i < numArguments; ++i) {
          if (arguments[i].getType() == null) {
            SimpleNode argNode = argumentListNode.jjtGetChild(i);
            DiagnosticsHandler.cantDeduceParameterType(argNode, staticName, signature, className);
            break;
          }
        }
        update(Codes.MAJOR_ERRORS);
      }
      // ERROR #5: Multiple overloads found
      else if (deduction.wereMultiple()) {
        DiagnosticsHandler.multipleOverloads(node, staticName, signature);
        update(Codes.MAJOR_ERRORS);
      }
      // All good!
      else {
        CallableDescriptor callable = deduction.getCallable();
        DAGStaticCall call = new DAGStaticCall(staticName, signature, callable, arguments);
        assertArgumentList(call, node);
        return call;
      }
    }

    return new DAGStaticCall(classDescriptor, staticName, signature, arguments);
  }

  /**
   * @param node A JJT member method call node.
   * @return A DAGMethodCall node.
   */
  private DAGMethodCall buildMethodCall(SimpleNode node) {
    assert node.is(JJTCALL);

    SimpleNode objectNode = node.jjtGetChild(0);
    SimpleNode methodNameNode = node.jjtGetChild(1);
    SimpleNode argumentListNode = node.jjtGetChild(2);
    assert methodNameNode.is(JJTMETHODNAME);
    assert argumentListNode.is(JJTARGUMENTLIST);

    DAGExpression object = build(objectNode);

    String methodName = methodNameNode.jjtGetVal();

    int numArguments = argumentListNode.jjtGetNumChildren();
    DAGExpression[] arguments = new DAGExpression[numArguments];
    TypeDescriptor[] types = new TypeDescriptor[numArguments];

    for (int i = 0; i < numArguments; ++i) {
      arguments[i] = build(argumentListNode.jjtGetChild(i));
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
      ClassDescriptor classDescriptor = (ClassDescriptor) objectType;
      String className = classDescriptor.getName();

      // ERROR #2: The method M is undefined for type T.
      if (!classDescriptor.hasMethod(methodName)) {
        DiagnosticsHandler.noSuchMethod(node, methodName, className);
        update(Codes.MAJOR_ERRORS);
      }
      // ERROR #3: No method M for type T matches the signature S.
      else if (!classDescriptor.hasMethod(methodName, signature)) {
        DiagnosticsHandler.noOverload(node, methodName, signature, className);
        update(Codes.MAJOR_ERRORS);
      }
      // Attempt deduction.
      else {
        Deduction deduction = classDescriptor.deduce(methodName, signature);

        // ERROR #4: Function is external, and some parameter has unknown type
        if (!deduction.wasSuccess()) {
          for (int i = 0; i < numArguments; ++i) {
            if (arguments[i].getType() == null) {
              SimpleNode argNode = argumentListNode.jjtGetChild(i);
              DiagnosticsHandler.cantDeduceParameterType(argNode, methodName, signature, className);
              break;
            }
          }
          update(Codes.MAJOR_ERRORS);
        }
        // ERROR #5: Multiple overloads found
        else if (deduction.wereMultiple()) {
          DiagnosticsHandler.multipleOverloads(node, methodName, signature);
          update(Codes.MAJOR_ERRORS);
        }
        // All good!
        else {
          CallableDescriptor callable = deduction.getCallable();
          DAGMethodCall call = new DAGMethodCall(object, methodName, signature, callable,
                                                 arguments);
          assertArgumentList(call, node);
          return call;
        }
      }
    }

    return new DAGMethodCall(object, methodName, signature, arguments);
  }

  /**
   * * Analysis of the problem of function signature deduction
   * ! Problem #1:
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
   * declaration before the function is used, hence the return type is known upfront (one solution
   * to the chicken-and-egg problem). In Java its even simpler: it just looks for the class in the
   * class database or classpath, and verifies what return type this overload has -- it either
   * exists or it doesn't, there's no chicken-and-egg problem to begin with.
   *
   * Now, we can't just reject this code. We also can't just assume it returns void, if it is used
   * in e.g.
   *
   *          int a;
   *          a = obj1.method1(a1, a2, a3, a4);
   *
   * The specification says we must accept this code. So method1 must be deduced to return a's type,
   * i.e. it must be deduced to return int. Then the return types of a1, a2, a3, a4 are not relevant
   * even if they aren't actually known.
   *
   * More generally, our solution to this problem is as follows: if the method1 call is used in a
   * context which expects a type T, it is assumed to return exactly T, and that return type is
   * assigned to the Callable held by the DAGCall and is used when writing bytecode. If the method1
   * call is a top-level statement, it is assumed
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
   * deduced, the types of b2 and b4 are known as well. We implemented this (return type overload
   * resolution), but the code got very complicated for a rather pointless (and actually dangerous)
   * feature, and made unknown-type propagation very difficult during semantic analysis (in this
   * class), so we removed the code. If the above case occurs, a gentle error is shown asking the
   * programmer to disambiguate the method call.
   *
   * The first case's problem stands. Here is the issue: JMM is not a normal programming
   * language. It has no type conversions (making the enterprise of return type overload resolution
   * considerably easier) and a very defficient specification: we must somehow write a compiler that
   * respects the two following requirements:
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
   * this is our solution to this problem: When a parameter type cannot be deduced and error is
   * diagnosed, in same fashion as before: the programmer is kindly asked to disambiguate his method
   * call.
   *
   * Return type of external function calls are assumed to be correct and deduced based on context.
   *
   * Top level function calls (aka statement calls, with no encapsulating expression or assignment)
   * are assumed to return void; this is handled in assertStatement(), and warrant warning
   * diagnostics as well.
   *
   * Everything else is handled in assertType(), where we provide the expected non-void return type.
   */

  /**
   * Asserts that the given DAGExpression node is of the given type. The SimpleNode corresponding to
   * this DAGExpression node is given as well. If the DAGExpression node is a DAGCall, verify
   * whether return type overload resolution should be applied.
   *
   * @param expression The DAGExpression node whose type is being checked.
   * @param type       The type the node should have.
   * @param node       The SimpleNode used to construct the expression, for diagnostics.
   */
  private void assertType(DAGExpression expression, TypeDescriptor expectedType, SimpleNode node) {
    assert expression != null && node != null;
    if (expectedType == null) return;

    if (expression instanceof DAGCall) {
      DAGCall call = (DAGCall) expression;

      if (call.isNotDeduced()) {
        deduceUnknownCallType(call, expectedType, node);
        return;
      }
    }

    if (expression.getType() != null && !typematch(expression.getType(), expectedType)) {
      System.out.println(expression.getType().toString());
      System.out.println(expectedType);
      System.out.println(((ClassDescriptor)expression.getType()).getSuper());
      DiagnosticsHandler.typeMismatch(node, expectedType, expression.getType());
      update(Codes.MAJOR_ERRORS);
    }

    return;
  }

  /**
   * Asserts that the given DAGExpression node is a top-level statement. The SimpleNode
   * corresponding to this DAGExpression node is given as well. If the DAGExpression node is a
   * DAGCall of an external function, verify whether return type overload resolution should be
   * applied. Otherwise, it should be a DAGCall or a DAGNew, because these are the only expression
   * which have side-effects. A binary-operator expression, for example, is unexpected in this
   * context as it has no side-effects.
   *
   * @param expression The DAGExpression node whose type is being verified.
   * @param node       The SimpleNode used to construct the expression, for diagnostics.
   */
  private void assertStatement(DAGExpression expression, SimpleNode node) {
    assert expression != null && node != null;

    if (expression instanceof DAGCall) {
      DAGCall call = (DAGCall) expression;

      if (call.isNotDeduced()) {
        deduceUnknownCallStatement(call, node);
        return;
      }
    }

    // Expression has side-effects?
    if (expression instanceof DAGCall || expression instanceof DAGNew) return;

    // No side-effects, issue diagnostic
    DiagnosticsHandler.invalidTopLevelExpression(node);
    update(Codes.WARNINGS);
  }

  private void deduceUnknownCallType(DAGCall call, TypeDescriptor type, SimpleNode node) {
    assert call.isNotDeduced();
    ((JavaCallableDescriptor) call.getCallable()).deduceReturnType(type);

    String functionName = call.getMethodName();
    FunctionSignature original = call.getOriginalSignature();
    DiagnosticsHandler.deducedReturnType(node, type, functionName, original);
  }

  private void deduceUnknownCallStatement(DAGCall call, SimpleNode node) {
    assert call.isNotDeduced();
    ((JavaCallableDescriptor) call.getCallable()).deduceReturnType(voidDescriptor);

    String functionName = call.getMethodName();
    FunctionSignature original = call.getOriginalSignature();
    DiagnosticsHandler.deducedReturnType(node, voidDescriptor, functionName, original);
  }

  private void assertArgumentList(DAGCall call, SimpleNode node) {
    assert node.is(JJTCALL);

    SimpleNode argumentListNode = node.jjtGetChild(2);
    assert argumentListNode.is(JJTARGUMENTLIST);

    CallableDescriptor callable = call.getCallable();

    for (int i = 0; i < call.getNumArguments(); ++i) {
      DAGExpression argument = call.getArgument(i);
      TypeDescriptor expectedType = callable.getParameterType(i);
      SimpleNode argumentNode = argumentListNode.jjtGetChild(i);

      assertType(argument, expectedType, argumentNode);
    }
  }
}
