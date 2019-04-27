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

## Implemented Descriptors Hierarchy
---

    a = abstract, c = public class, p = package-private class
    h = overrides hashCode() and equals()

    p Descriptor
        ah TypeDescriptor
            c PrimitiveDescriptor
            a ClassDescriptor
                c JMMClassDescriptor
                c UnknownClassDescriptor
            c VoidDescriptor
        ah VariableDescriptor
            ch LocalDescriptor
            ch ParameterDescriptor
            ch MemberDescriptor
        aph BaseFunctionDescriptor
            ch FunctionDescriptor
                c MethodDescriptor
                c StaticMethodDescriptor
            ch JMMMainDescriptor
        ch FunctionLocals

## Suggested DAG Hierarchy
---

    a = abstract, i = interface, c = public class, p = package-private class
    d = Any two instances of this node are different
    h = overrides hashCode() and equals()
    [...] = expression type

    a DAGNode                       [~varies]
        a DAGExpression             [~varies]
            c DAGIntegerConstant    [int]
            c DAGBooleanConstant    [boolean]
            c DAGVariable           [type(variable)]
                c DAGThis           [type(this)]
            ad DAGNew               [~varies]
                c DAGNewIntArray    [int[]]
                c DAGNewClass       [type(class)]
            c DAGLength             [int]
            ap DAGUnaryOp           [~varies]
                c DAGNot            [boolean]
            c DAGBinaryOp           [+,-,*,/ -> int; <,&& -> boolean]
            c DAGBracket            [int]
            ad DAGCall              [return(function)]
                cd DAGMethodCall    [return(function)]
                cd DAGStaticCall    [return(function)]
        cd DAGAssignment
            cd DAGBracketAssignment

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
        MUL                [2] Expression Expression
        DIV                [2] Expression Expression
        Bracket            [2] ExpressionT Expression
        --
        Call               [3] ExpressionT MethodName ArgumentList
        --
        ArgumentList       [n] [Expression, ...]

### DAG Methods

    DAGNode
        DAGExpression
         + getType() -> TypeDescriptor
            DAGIntegerConstant
             + getValue() -> int
            DAGBooleanConstant
             + getValue() -> boolean
            DAGVariable
             + getVariable() -> VariableDescriptor
                DAGThis
                 + getVariable() -> LocalDescriptor
            DAGNew
                DAGNewIntArray
                 + getIndexExpression() -> DAGExpression(int)
                DAGNewClass
                 + getClassDescriptor() -> ClassDescriptor
            DAGLength
             + getExpression() -> DAGExpression(int[])
            DAGNot
             + getExpression() -> DAGExpression(boolean)
            DAGBinaryOp
             + getOperator() -> BinaryOperator {+, -, *, /, <, &&}
             + getLhs() -> DAGExpression(int or boolean)
             + getRhs() -> DAGExpression(int or boolean)
             + isArithmetic() -> boolean
             + isComparison() -> boolean
             + getOperandType() -> int or boolean
            DAGBracket
             + getArrayExpression() -> DAGExpression(int[])
             + getIndexExpression() -> DAGExpression(int)
            DAGCall
             + isStatic() -> boolean
             + getCallClass() -> ClassDescriptor
             + getMethodName() -> String
             + getSignature() -> FunctionSignature, possibly incomplete
                DAGMethodCall
                 + getObjectExpression() -> DAGExpression(classtype)
                 > isStatic() -> false
                 > getCallClass() -> class of expression
                DAGStaticCall
                 > isStatic() -> true
                 > getCallClass() -> identifier class
