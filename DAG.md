
# DAG: package compiler.dag

---

## DAG Hierarchy (8 May 2019)

    a = abstract, i = interface, c = class, p = package-private
    e = common subexpression elimination: two instances of this class may be equal
    n = cancel subexpression elimination: two instances of this class cannot be equal
    [...] = result of getType()

    a   DAGNode
        ae  DAGExpression               [~varies]
            ce  DAGIntegerConstant      [int]
            ce  DAGBooleanConstant      [boolean]
            ce  DAGVariable             [typeof(variable)]
                ne  DAGMember           [any]
                i   LocalVariable
                    ce  DAGLocal        [any]
                    ce  DAGParameter    [any]
                    ce  DAGThis         [typeof(this), classtype]
            anp DAGNew
                cn  DAGNewIntArray      [int[]]
                cn  DAGNewClass         [classtype]
            ce  DAGLength               [int]
            ce  DAGNot                  [boolean]
            cn  DAGBracket              [int]
            ce  DAGBinaryOp             [+,-,*,/ -> int;  <,&& -> boolean]
            an  DAGCall                 [return(function)]
                cn  DAGMethodCall
                cn  DAGStaticCall
        ce  DAGCondition                [boolean]
        c   DAGAssignment               [typeof(variable)]
            c   DAGBracketAssignment    [int]
        ap  DAGBranch                   -
            c   DAGIfElse               -
            c   DAGWhile                -
        c   DAGMulti                    -
        a   DAGReturn
            c   DAGReturnExpression     [typeof(expression)]
            c   DAGVoidReturn           [void]

## Factories (8 May 2019)

    a = abstract, i = interface
    c = public class, p = package-private class
    r = reuses nodes

      ap   BaseDAGFactory
        c    NodeFactory
        c    AssignmentFactory (used by NodeFactory)
        cr   ExpressionFactory (used by NodeFactory)

## AST children for Expressions (8 May 2019)

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
