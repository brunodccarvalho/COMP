
# Javac Implementation of JVM

    Notation:
          $EXPRESSION_A...   Recursion: EXPRESSION_A is a child of the current node. Its code should
                             be placed in this exact position.
        ? EXPRESSION_B       EXPRESSION_B isn't always present, only if result is to be cached.
                             Usual when evaluating expressions.
      L:  EXPRESSION_C       L is a label, used to jump from elsewhere to EXPRESSION_C.


    DAGBinaryOp> 1/6
      Add:
          $lhs...
          $rhs...
          iadd
        ? istore                  Store result of lhs + rhs

    DAGBinaryOp> 2/6
      Sub:
          $lhs...
          $rhs...
          isub
        ? istore                  Store result of lhs - rhs

    DAGBinaryOp> 3/6
      Mul:
          $lhs...
          $rhs...
          imul
        ? istore                  Store result of lhs * rhs

    DAGBinaryOp> 4/6
      Div:
          $lhs...
          $rhs...
          idiv
        ? istore                  Store result of lhs / rhs

    DAGBinaryOp> 5/6
      Less:
          $lhs...
          $rhs...
          if_icmpge   A
          iconst_1
          goto        B
      A:  iconst_0
      B:? istore                  Store result of lhs < rhs

    DAGBinaryOp> 6/6
      And:
          $lhs...
          ifeq        A
          $rhs...
          ifeq        A
          iconst_1
          goto        B
      A:  iconst_0
      B:? istore                  Store result of lhs && rhs

    DAGLength:
          $loadarray...
          arraylength
        ? istore                  Store result of loadarray.length

    DAGNot:
          $loadboolean...
          ifne        A
          iconst_1
          goto        B
      A:  iconst_0
      B:? istore                  Store result of !loadboolean

    DAGBracket:
          $loadarray...
          $loadindex...
          iaload
        ? istore                  Store result of loadarray[loadindex]

    DAGNewClass:
          new <class>
          dup                     # don't dup if top-level expression
          invokespecial <init>:()V
        ? astore                  Store result of new Class()

    DAGNewIntArray:
          $loadcount...
          newarray int
        ? astore                  Store result of new int[loadcount]

    DAGIntegerConstant:
          iconst_<n>              # for -1, 0, 1, 2, 3, 4, 5
        or
          bipush <constant>       # else for -128, ..., 127
        or
          sipush <constant>       # else for -32768, ..., 32767
        or
          ldc    <constant>       # else

    DAGBooleanConstant:
          iconst_0                # false
        or
          iconst_1                # true

    ***** DAGCall

    DAGCall> 1/4
      DAGMethodCall --> void:
          $loadobjectref...       # possibly aload_0
          $loadarg1...
          $loadarg2...
          ...
          invokevirtual <method descriptor>

    DAGCall> 2/4
      DAGStaticCall --> void:
          $loadarg1...
          $loadarg2...
          ...
          invokestatic <method descriptor>

    DAGCall> 3/4
      DAGMethodCall --> non void:
          $loadobjectref...       # possibly aload_0
          $loadarg1...
          $loadarg2...
          ...
          invokevirtual <method descriptor>
        ? [ia]store               Store result of method call
          [pop]                   se for um statement

    DAGCall> 4/4
      DAGStaticCall --> non void:
          $loadarg1...
          $loadarg2...
          ...
          invokestatic <method descriptor>
        ? [ia]store               Store result of static call
          [pop]                   se for um statement

    ***** DAGVariable (reads)

    DAGVariable> 1/3
      DAGLocal, DAGParameter:
          aload_n
        or
          iload_n
        or
          aload <index>
        or
          iload <index>

    DAGVariable> 2/3
      DAGThis:
          aload_0

    DAGVariable> 3/3
      DAGMember:
          aload_0
          getfield <member> <type>

    ***** Assignment (stores)

    IMPORTANT: Order of evaluation: receiving expression, [index expression], value expression.

    DAGAssignment> 1/2
      DAGAssignment to DAGMember:
          aload_0                 # load this
          $loadvalue...           # evaluate value
          putfield  <member> <type>
          ... CONTINUE

    DAGAssignment> 2/2
      DAGAssignment to DAGLocal or DAGParameter:
          $loadvalue...           # evaluate value
          [i|a]store VAR          # store appropriate type to local
          ... CONTINUE

    DAGBracketAssignment>
      DAGBracketAssignment to DAGLocal or DAGParameter or DAGMember:
          $loadarray...           # evaluate receiving expression, possibly getfield...
          $loadindex...           # evaluate index
          $loadvalue...           # evaluate value
          iastore VAR             # store int in int array
          ... CONTINUE

    ***** Return Statements

    DAGVoidReturn:
          return
          ... CONTINUE

    DAGReturnExpression> 1/2
      Return is int or boolean:
          $returnexpression
          ireturn
          ... CONTINUE

    DAGReturnExpression> 2/2
      Return is reference:
          $returnexpression
          areturn
          ... CONTINUE

    ***** Control Flow

    DAGIfElse> 1/3
      DAGCondition of boolean type (variable load, function call, etc):
          $conditionexpression...
          ifeq        A
          $truebranch...
          goto        B           # only necessary if $truebranch does not return
      A:  $falsebranch...
      B:  ... CONTINUE

    DAGIfElse> 2/3
      DAGCondition is BinaryOperation Less (<):
          $lhs...
          $rhs...
          if_icmpge   A
          $truebranch...
          goto        B           # only necessary if $truebranch does not return
      A:  $falsebranch...
      B:  ... CONTINUE
      # NOTA: DAGLess é um caso especial com $truebranch = iconst_1 e $falsebranch = iconst_0

    DAGIfElse> 3/3
      DAGCondition is BinaryOperation And (&&):
          $lhs...
          ifeq        A
          $rhs...
          ifeq        A
          $truebranch...
          goto        B           # only necessary if $truebranch does not return
      A:  $falsebranch...
      B:  ... CONTINUE
      # NOTA: DAGAnd é um caso especial com $truebranch = iconst_1 e $falsebranch = iconst_0

    DAGWhile> 1/3
      DAGCondition of boolean type (variable load, function call, etc):
      A:  $conditionexpression...
          ifeq        B
          $body...
          goto        A           # only necessary if $body does not return unconditionally
      B:  ... CONTINUE

    DAGWhile> 2/3
      DAGCondition is BinaryOperation Less (<):
      A:  $lhs...
          $rhs...
          if_icmpge   B
          $body...
          goto        A           # only necessary if $body does not return unconditionally
      B:  ... CONTINUE

    DAGWhile> 3/3
      DAGCondition is BinaryOperation And (&&):
      A:  $lhs...
          ifeq        B
          $rhs...
          ifeq        B
          $body...
          goto        A           # only necessary if $body does not return unconditionally
      B:  ... CONTINUE

    DAGMulti:
          $child0...
          $child1...
          $child2...
          ...
          ... CONTINUE


# Done

## DAG code generation

    + means correct
    - means incorrect
      means i didnt find it anywhere
    ? means i dont know

    + DAGBinaryOp 1/6 (Add, +)
    + DAGBinaryOp 2/6 (Sub, -)
    + DAGBinaryOp 3/6 (Mul, *)
    + DAGBinaryOp 4/6 (Div, /)
    - DAGBinaryOp 5/6 (Less, <)  completely different, involves jumps
    - DAGBinaryOp 6/6 (And, &&)  completely different, involves jumps
      DAGLength
      DAGNot
    - DAGBracket   should be iastore
      DAGNewClass
    ? DAGNewIntArray       jasmin uses 'newarray int' instead
    + DAGIntegerConstant
    + DAGBooleanConstant
      DAGCall 1/4 (Method Call -> void)         see code above
      DAGCall 2/4 (Static Call -> void)         see code above
      DAGCall 3/4 (Method Call -> not void)     see code above
      DAGCall 4/4 (Static Call -> not void)     see code above
    ? DAGVariable 1/3 (Local and Parameter)
    ? DAGVariable 2/3 (this)
    ? DAGVariable 3/3 (Member)
    ? DAGAssignment 1/2 (to Member)
    ? DAGAssignment 2/2 (to Local or Parameter)
    - DAGBracketAssignment               should be iastore
      DAGVoidReturn
      DAGReturnExpression 1/2 (primitives)
      DAGReturnExpression 2/2 (references)
    + DAGIfElse 1/3 (boolean)   correct, ifeq
    - DAGIfElse 2/3 (Less, <)   if_icmpge
    - DAGIfElse 3/3 (And, &&)   very different, needs short-circuiting
    + DAGWhile 1/3 (boolean)   correct, ifeq
    - DAGWhile 2/3 (Less, <)   if_icmpge
    - DAGWhile 3/3 (And, &&)   very different, needs short-circuiting
    + DAGMulti

## More method body code

    . Pop return values of top-level statement-expressions.
    --> implement in MethodBodyGenerator.generateStatement(DAGNode),
        appending to expression a pop instruction.


## General code generation

    Generate method body bytecode
    Generate method header
    Generate method
    Generate class headers bytecode
    Generate class bytecode
