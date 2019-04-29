## Compiler Passes
---

4 Passes.

### 1. Class metadata

1. Create one JMM Class symbol table, which includes
    * [JMM0] Extends clause
    * [JMM1] Package-protected data members
    * [JMM2] Public member methods
    * [JMM3] Zero or one static main method

2. Read the member data variables, populating JMM1.
    * Type and name for each of them.
    * Deduce class types wherever necessary.
    * Ensure there are no conflicts.

3. Read the method declarations (methods and static main), populating JMM2 and JMM3.
    * Return type and parameter list for each of them.
    * Deduce class types wherever necessary.
    * Ensure there are no conflicts.

Member functions should be searchable by name.

### 2. Function local variables

Keep in mind member methods have (implicit) access to 'this' while the
main method doesn't.

Read the first i local variable declarations for each function and
verify there are no conflicts with parameter names. For member methods these __can__ be in
conflict with the implicit member variables -- the locals will take precedence.

### 3. Function return statement

For the member methods, read the return statement of the function.
The return type should be deduceable by now. It should agree with the
declared function return type or be convertible to it.

### 4. Function statements

Parse the function statements in depth-first order.

## Descriptors [compiler.symbols]

---

### Function Descriptor Hierarchy (28-04-2019)

    a = abstract, i = interface
    c = public class, p = package-private class

    i Function
        i JMMFunction       isJMM() -> true
        i JavaFunction      isJMM() -> false
        i MethodFunction    isStatic() -> false
        i StaticFunction    isStatic() -> true
        i Callable          getSignature()

    p BaseFunctionDescriptor                  i Function
        c JMMMainDescriptor                    i JMMFunction, StaticFunction
        a CallableDescriptor                   i Callable
            a JavaCallableDescriptor            i JavaFunction
                c JavaMethodDescriptor           i MethodFunction
                c JavaStaticMethodDescriptor     i StaticFunction
            a JMMCallableDescriptor             i JMMFunction
                c JMMMethodDescriptor            i MethodFunction
                c JMMStaticMethodDescriptor      i StaticFunction

    FunctionLocals uses JMMFunction
    ParameterDescriptor uses JMMCallableDescriptor

    Possibilities for future expansion:
        i CallableMethod   i Callable, MethodFunction
        i CallableStatic   i Callable, StaticFunction
    DAGCall uses Callable
    DAGMethodCall uses CallableMethod
    DAGStaticCall uses CallableStatic

### Type Descriptor Hierarchy

    a = abstract, i = interface
    c = public class, p = package-private class

    a TypeDescriptor
        a ClassDescriptor
            c UnknownClassDescriptor
            c JMMClassDescriptor
        c VoidDescriptor
        c PrimitiveDescriptor (int, int[], boolean)

### Variable Descriptor Hierarchy

    a = abstract, i = interface
    c = public class, p = package-private class

    a VariableDescriptor
        c MemberDescriptor
        c ParameterDescriptor
        c LocalDescriptor
        c ThisDescriptor

### Extra Descriptors

    a = abstract, i = interface
    c = public class, p = package-private class

    ap Descriptor (base of all descriptors)
        c FunctionLocals

## DAG [compiler.dag]

---

### DAG Hierarchy

    a = abstract, i = interface
    c = public class, p = package-private class
    d = Any two instances of this node are different
    [...] = expression type

    a DAGNode
        a DAGExpression             [~varies]
            c DAGIntegerConstant    [int]
            c DAGBooleanConstant    [boolean]
            c DAGVariable           [type(variable)]
                c DAGThis           [type(this class)]
            ad DAGNew               [~varies]
                c DAGNewIntArray    [int[]]
                c DAGNewClass       [type(class)]
            c DAGLength             [int]
            c DAGNot                [boolean]
            c DAGBinaryOp           [+,-,*,/ -> int; <,&& -> boolean]
            c DAGBracket            [int]
            ad DAGCall              [return(function)]
                c DAGMethodCall     [return(function)]
                c DAGStaticCall     [return(function)]
        cd DAGAssignment
            c DAGBracketAssignment

        ...Multi expressions... (checkpoint 3)
        ...Control flow... (checkpoint 3)

### AST branches for an Expression

    SimpleNode
        Integer            [0]
        Identifier         [0]
        True               [0]
        False              [0]
        This               [0]
        --
        NewIntArray        [1] Expression
        NewClass           [1] ClassType
        Length             [1] ExpressionT
        NOT                [1] ExpressionT
        --
        AND                [2] ExpressionT Expression
        LT                 [2] ExpressionT Expression
        SUM                [2] ExpressionT Expression
        SUB                [2] ExpressionT Expression
        MUL                [2] Expression  Expression
        DIV                [2] Expression  Expression
        Bracket            [2] ExpressionT Expression
        --
        Call               [3] ExpressionT MethodName ArgumentList
        --
        ArgumentList       [n] Expression, ...

### DAG Methods

    DAGNode
        DAGExpression
         + getType() -> TypeDescriptor
            DAGIntegerConstant
             + getValue() -> int
             ! getType() -> int
            DAGBooleanConstant
             + getValue() -> boolean
             ! getType() -> int
            DAGVariable
             + getVariable() -> VariableDescriptor
                DAGThis
                 + getVariable() -> ThisDescriptor
                 ! getType() -> JMMClassDescriptor
            DAGNew
                DAGNewIntArray
                 + getIndexExpression() -> DAGExpression(int)
                 ! getType() -> int
                DAGNewClass
                 + getClassDescriptor() -> ClassDescriptor
                 ! getType() -> ClassDescriptor
            DAGLength
             + getExpression() -> DAGExpression(int[])
             ! getType() -> int
            DAGNot
             + getExpression() -> DAGExpression(boolean)
             ! getType() -> boolean
            DAGBinaryOp
             + getOperator() -> BinaryOperator {+, -, *, /, <, &&}
             + getLhs() -> DAGExpression(int or boolean)
             + getRhs() -> DAGExpression(int or boolean)
             + isArithmetic() -> true for +,-,*,/
             + isComparison() -> true for <
             + getOperandType() -> int or boolean
             ! getType() -> int or boolean
            DAGBracket
             + getArrayExpression() -> DAGExpression(int[])
             + getIndexExpression() -> DAGExpression(int)
             ! getType() -> int
            DAGCall
             + isStatic() -> boolean
             + getCallClass() -> ClassDescriptor
             + getMethodName() -> String
             + getSignature() -> FunctionSignature, possibly incomplete
             + getNumArguments() -> int
             + getArguments() -> DAGExpression[num args]
             + getArgument(i) -> DAGExpression @ i
                DAGMethodCall
                 + getObjectExpression() -> DAGExpression(classtype)
                 > isStatic() -> false
                 > getCallClass() -> type of expression
                DAGStaticCall
                 > isStatic() -> true
                 > getCallClass() -> identifier class
