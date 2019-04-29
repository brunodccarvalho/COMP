# Instruction Sets

## JVM Instruction Set

---

### JVM Types

    [word] Category 1 types:
      + boolean
      - byte
      - char
      - short
      + int
      - float
      + reference
      + returnAddress.
    [dword] Category 2 types:
      - long
      - double

Support required by JMM:

* Partial support for boolean (no arrays)
* Full support for ```int```
* Partial support for references (no arrays)

### JVM Instructions for JMM

#### 1. Runtime constant pool

    + ldc
      $index
            ...
        --> ..., value
      Push item from runtime constant pool, short.

    + ldc_w
      $indexbyte1
      $indexbyte2
            ...
        --> ..., value
      Push item from runtime constant pool, wide.

#### 2. Stack manipulators

    + dup
            ..., word
        --> ..., word, word
      Duplicate word on the top of the stack.

    + dup_x1
            ..., word_2, word_1
        --> ..., word_1, word_2, word_1
      Duplicate word on the top of the stack under a word beneath it.

    + dup_x2
            ..., word_3, word_2, word_1
        --> ..., word_1, word_3, word_2, word_1
            ..., dword_2,  word_1
        --> ...,  word_1, dword_2,  word_1
      Duplicate word on the top of the stack under a dword beneath it.

    + dup2
            ..., word_2, word_1
        --> ..., word_2, word_1, word_2, word_1
            ..., dword
        --> ..., dword, dword
      Duplicate dword on the top of the stack.

    + dup2_x1
            ...,  word_3,  word_2,  word_1
        --> ...,  word_2,  word_1,  word_3,  word_2,  word_1
            ...,  word_2, dword_1
        --> ..., dword_1,  word_2, dword_1
      Duplicate dword on the top of the stack under a word beneath it.

    + dup2_x2
            ...,  word_4,  word_3,  word_2,  word_1
        --> ...,  word_2,  word_1,  word_4,  word_3,  word_2,  word_1
            ...,  word_3,  word_2, dword_1
        --> ..., dword_1,  word_3,  word_2, dword_1
      Duplicate dword on the top of the stack under a dword beneath it.

    + pop
            ...,  word
        --> ...
      Pop word on the top of the stack.

    + pop2
            ...,  word_1,  word_2
        --> ...
      Pop top two values on the stack.
            ..., dword
        --> ...
      Pop dword on the top of the stack.

    + swap
            ...,  word_1,  word_2
        --> ...,  word_2,  word_1
      Swap the two words on the top of the stack.

#### 3. Branch instructions

##### Branch unconditionally

    + goto
      $branchbyte1
      $branchbyte2
            -- no stack change
      Branch unconditionally.
      The unsigned bytes branchbyte1 and branchbyte2 are used to construct an offset.
      The value of the offset is
        0x$branchbyte1$branchbyte2, a 16-bit integer.
      Execution proceeds at that offset from the address of the opcode of this
        goto instruction. The target address must be that of an opcode of an
        instruction within the method that contains this goto instruction.

    + goto_w
      $branchbyte1
      $branchbyte2
      $branchbyte3
      $branchbyte4
            -- no stack change
      Branch unconditionally, wide.
      The unsigned bytes branchbyte1, branchbyte2, branchbyte3 and branchbyte4
        are used to construct an offset.
      The value of the offset is
        0x$branchbyte1$branchbyte2$branchbyte3$branchbyte4, a 32-bit integer.
      Execution proceeds at that offset from the address of the opcode of this
        goto instruction. The target address must be that of an opcode of an
        instruction within the method that contains this goto instruction.

    + jsr
      $branchbyte1
      $branchbyte2
            ...
        --> ..., address
      Jump subroutine.
      The address of the opcode of the instruction immediately following this jsr
        instruction is pushed onto the stack with type returnAddress. The execution
        continues at the branch position.

    + jsr_w
      $branchbyte1
      $branchbyte2
      $branchbyte3
      $branchbyte4
            ...
        --> ..., address
      Jump subroutine, wide.
      The address of the opcode of the instruction immediately following this jsr
        instruction is pushed onto the stack with type returnAddress. The execution
        continues at the branch position.

    + ret
      $index
            -- no stack change
      Continue execution from address taken from a local variable $index.
      Notice assymmetry with jsr and jsr_w.

##### Branch conditionally

    if_acmp<cond>
      $branchbyte1
      $branchbyte2
      <cond> = eq, ne
            ..., objectref1, objectref2
        --> ...
      Branch if reference comparison succeeds.
        eq: equals:
          succeeds iff objectref1 == objectref2.
        ne: not equals:
          succeeds iff objectref1 != objectref2.

    + if_icmp<cond>
      $branchbyte1
      $branchbyte2
      <cond> = eq, ne, lt, gt, le, ge
            ..., int1, int2
        --> ...
      Branch if integer comparison succeeds.
        eq: equals:
          succeeds iff int1 == int2.
        ne: not equals:
          succeeds iff int1 != int2.
        lt: less than:
          succeeds iff int1 < int2.
        gt: greater than:
          succeeds iff int1 > int2.
        le: less than or equals:
          succeeds iff int1 <= int2.
        ge: greater than or equals:
          succeeds iff int1 >= int2.

    + if<cond>
      $branchbyte1
      $branchbyte2
      <cond> = eq, ne, lt, gt, le, ge
            ..., int1
        --> ...
      Branch if integer comparison with 0 succeeds.
        eq: equals:
          succeeds iff int1 == 0.
        ne: not equals:
          succeeds iff int1 != 0.
        lt: less than:
          succeeds iff int1 < 0.
        gt: greater than:
          succeeds iff int1 > 0.
        le: less than or equals:
          succeeds iff int1 <= 0.
        ge: greater than or equals:
          succeeds iff int1 >= 0.

#### 4. References

    + aconst_null
            ...
        --> ..., null
      Push the null object reference onto the operand stack.

    + aload
      $index
            ...
        --> ..., objectref
      Load reference from local variable @index.

    + aload_n
      <n> = 0,1,2,3
            ...
        --> ..., objectref
      Load reference from local variable, small index.

    + astore
      $index
            ..., objectref
        --> ...
      Store reference into local variable @index.

    + astore_n
      <n> = 0,1,2,3
            ..., objectref
        --> ...
      Store reference into local variable, small index.

#### 5. Classes

    + new
      $indexbyte1
      $indexbyte2
            ...
        --> ..., objectref

    + getfield
      $indexbyte1
      $indexbyte2
            ..., objectref
        --> ..., value
      Fetch field from object.
      The objectref must be of type reference, non-array.

    + getstatic
      $indexbyte1
      $indexbyte2
            ...
        --> ..., value
      Get static field from class.
      The objectref must be of type reference, non-array.

    + putfield
      $indexbyte1
      $indexbyte2
            ..., objectref, value
        --> ...
      Set object's member field.

    + putstatic
      $indexbyte1
      $indexbyte2
            ..., value
        --> ...
      Set static field to value in a class.

    + invokedynamic
      $indexbyte1
      $indexbyte2
      0
      0
            ..., arg1, arg2, ...
        --> ...
      Invoke a dynamic method.

    - invokeinterface
      $indexbyte1
      $indexbyte2
      $count
      0
            ..., objectref, arg1, arg2, ...
        --> ...
      Invoke an interface method.

    + invokespecial
      $indexbyte1
      $indexbyte2
            ..., objectref, arg1, arg2, ...
        --> ...
      Invoke instance method; special handling for superclass, private,
        and instance initialization method invocations.

    + invokestatic
      $indexbyte1
      $indexbyte2
            ..., arg1, arg2, ...
        --> ...
      Invoke a class static method.

    + invokevirtual
      $indexbyte1
      $indexbyte2
            ..., objectref, arg1, arg2, ...
        --> ...
      Invoke instance method, dispatch based on class

#### 6. Arrays

    + newarray
      $atype
            ..., count
        --> ..., arrayref
      Create new array with count elements of primitive type identified by atype.

    + arraylength
            ..., arrayref
        --> ..., length
      Get length of array.

#### 7. Integers and Booleans

    + bipush
      $byte
            ...
        --> ..., int
      Push byte $byte.
      The immediate byte is sign-extended to an int value.

    + sipush
      $byte1
      $byte2
            ...
        --> ..., int
      Push short 0x$byte1$byte2.
      The immediate short is sign-extended to an int value.

    + iinc
      $index
      $const
            -- no stack change
      Increment local variable by constant.

    + iconst_i
      <i> = m1,0,1,2,3,4,5
            ...
        --> ..., int
      Push small constant integer (-1, 0, 1, 2, 3, 4, 5) onto the operand stack.
      Use iconst_0 to push false.
      Use iconst_1 to push true.

    + iadd
            ..., int1, int2
        --> ..., intsum
      Add two ints.

    + isub
            ..., int1, int2
        --> ..., intsub
      Subtract two ints.

    + imul
            ..., int1, int2
        --> ..., intmul
      Multiply two ints.

    + idiv
            ..., int1, int2
        --> ..., intdiv
      Divide two ints.

    + iaload
            ..., arrayref, index
        --> ..., int
      Load int from array of ints.

    + iastore
            ..., arrayref, index, int
        --> ...
      Store int into array of ints.

    + iload
      $index
            ...
        --> ..., int
      Load int from local variable.

    + iload_n
      <n> = 0,1,2,3
            ...
        --> ..., int
      Load int from local variable, small index.

    + istore
      $index
            ..., value
        --> ...
      Store int into local variable.

    + istore_n
      <n> = 0,1,2,3
            ..., value
        --> ...
      Store int into local variable, small index.

    + ishl
            ..., int1, int2
        --> ..., intresult
      Shift int1 left by s bit positions.
      s = int2 & 0x01f (lowest five bits of int2).

    + ishr
            ..., int1, int2
        --> ..., intresult
      Arithmetic shift int1 right by s bit positions.
      s = int2 & 0x01f (lowest five bits of int2).

    + iushr
            ..., int1, int2
        --> ..., intresult
      Logical shift int1 right by s bit positions.
      s = int2 & 0x01f (lowest five bits of int2).

#### 8. Return Statements

    + areturn
            ..., objectref
        --> empty
      Return reference from method.

    + return
            ...
        --> empty
      Return void from method.

    + ireturn
            ..., int
        --> ...
      Return int or boolean from method.

#### 9. Miscellaneous

    + nop
            -- no stack change
      Perform no operation.

## Jasmin Instruction Set

---

    1. Runtime constant pool
        ldc               <constant>
        ldc_w             <constant>

    2. Stack manipulators
        dup
        dup_x1
        dup_x2
        dup2
        dup2_x1
        dup2_x2
        pop
        pop2
        swap

    3. Branch instructions
        goto              <label>
        goto_w            <label>
        jsr               <label>
        jsr_w             <label>
        if_acmp<cond>     <label>
        if_icmp<cond>     <label>
        if<cond>          <label>
        ret               <var-num>

    4. References
        aconst_null
        aload             <var-num>
        aload_n
        astore            <var-num>
        astore_n

    5. Classes
        new               <class>
        getfield          <field-spec> <descriptor>
        getstatic         <field-spec> <descriptor>
        putfield          <field-spec> <descriptor>
        putstatic         <field-spec> <descriptor>
        invokenonvirtual  <method-spec>
        invokestatic      <method-spec>
        invokevirtual     <method-spec>

    6. Arrays
        newarray          <array-type>
        arraylength

    7. Integers and Booleans
        bipush            <constant>
        sipush            <constant>
        iinc              <var-num> <constant>
        iconst_i
        iadd
        isub
        imul
        idiv
        iaload
        iastore
        iload             <var-num>
        iload_n
        istore            <var-num>
        istore_n
        ishl
        ishr
        iushr

    8. Return Statements
        areturn
        return
        ireturn

    9. Miscellaneous
        nop

    <constant> is an integer, float, or quoted string.

    <array-type> is int.

    <var-num> refers to a local variable index.

    <class> is a class name, qualified with '/', e.g:
      new java/lang/String

    <label> is an assembly label, e.g:
    Label1:
      goto Label1

    <field-spec> is composed of two parts, a classname
    and a fieldname. The classname ends at the last '/',
    and the fieldname starts after the last '/', e.g:
      foo/bar/baz/Class/classMember

    <descriptor> is the Java type descriptor of the field.
      Ljava/io/PrintStream

    <method-desc> is composed of three parts, a classname,
    a methodname and a method descriptor, e.g:
      foo/bar/baz/class/classMethod(II)V

## Uses of Jasmin Instructions by each DAG node

### Uses in DAGExpression calculation

    DAGIntegerConstant:
      [7] iconst_n
      [7] bipush 100
      [7] sipush 10000
      [1] ldc 1000000

    DAGBooleanConstant:
      [7] iconst_0, iconst_1

    DAGLocalVariable:
    DAGParameterVariable:
      [4] aload
      [4] aload_n
      [7] iload
      [7] iload_n

    DAGMemberField:
      [5] getfield

    DAGNewIntArray:
      [6] newarray

    DAGNewClass:
      [5] new

    DAGLength:
      [6] arraylength

    DAGNot:

    DAGBinaryOp:
      ... lots of stuff

    DAGBracket:
      [4] aload
      [7] iaload

    DAGCall:
      invoke*

    DAGAssignment:
      ...

    DAGBracketAssignment:
      [7] iastore

### Simplified implementation suggestion

If a non-terminal DAGExpression has more than one parent, it should store its result in a local variable of
the JVM the first time that it is calculated. Instead of recalculating its value on the stack after the first
calculation, the saved value is loaded instead.

This does not apply to non-equals classes (DAGNew and DAGCall) and does not apply to constants.
It applies to DAGBinaryOp, DAGBracket, DAGLength and DAGNot.

    x = not eXpression
    r = Reusable
    c = Constant
    n = Not reusable

    x DAGAssignment
    r DAGBinaryOp
    c DAGBooleanConstant
    r DAGBracket
    x DAGBracketAssignment
    c DAGIntegerConstant
    r DAGLength
    n DAGMethodCall
    n DAGNewClass
    n DAGNewIntArray
    r DAGNot
    n DAGStaticCall
    c DAGVariable

    DAGBinaryOp: (DAGExpression lhs) Operator (DAGExpression rhs)
        generate lhs operand
                                      ...
                                  --> ..., lhs
        generate rhs operand
                                      ..., lhs
                                  --> ..., lhs, rhs
        apply operation
          if add, sub, mul, div:
                        iadd, isub, imul, idiv
          if less:
                        if_icmpge A
                        iconst_1
                        goto      B
                    A:  iconst_0
                    B:  ...
                                      ..., lhs, rhs
                                  --> ..., result
          if and (must implement shortcircuiting):

    DAGAnd:
        generate lhs operand
                                      ...
                                  --> ..., lhs
        if false, evaluate to 0 immediately:
                        ifeq     A
        else generate rhs operand
                                      ..., lhs
                                  --> ..., lhs, rhs
        if false, evaluate to 0, else evaluate to 1:
                        ifeq     A
                        iconst_1
                        goto     B
                    A:  iconst_0
                    B:  ...

    DAGLength: (DAGExpression ar) .length
        generate arrayref ar:
                                      ...
                                  --> ..., arrayref
        apply arraylength:
                        arraylength
                                      ..., arrayref
                                  --> ..., length

    DAGNot: NOT (DAGExpression b)
        generate boolean expression b:
                                      ...
                                  --> ..., boolean
        invert the boolean:
                        ifne      A
                        iconst_1
                        goto      B
                    A:  iconst_0
                    B:  ...

    DAGBracket: (DAGExpression ar) [DAGExpression index]
        generate arrayref ar:
                                      ...
                                  --> ..., arrayref
        generate index:
                                      ..., arrayref
                                  --> ..., arrayref, index
        fetch int:
                        iaload
                                      ..., arrayref, index
                                  --> ..., int

    * DAGNewClass:
        apply new:
                        new ClassName
                                      ...
                                  --> ..., objectref
                        dup
                                      ..., objectref
                                  --> ..., objectref, objectref
        call constructor:
                        invokespecial <constructor descriptors>
                                      ..., objectref, objectref
                                  --> ..., objectref

    * DAGNewIntArray: new int[DAGExpression count]
        generate count expression:
                                      ...
                                  --> ..., count
        apply new int array:
                        newarray int
                                      ..., count
                                  --> ..., arrayref

    * DAGIntegerConstant:
        for -1, 0, 1, 2, 3, 4, 5:
                        iconst_<n>
        else for -128, ..., 127:
                        bipush <constant>
        else for -32768, ..., 32767:
                        sipush <constant>
        else:
                        ldc    <constant>

    * DAGBooleanConstant:
        for true:
                        iconst_1
        for false:
                        iconst_0

    DAGAssignment: DAGVariable var = (DAGExpression value)
        if variable is Member:
          push 'this' onto the stack:
                        aload_0
                                      ...
                                  --> ..., this=objectref
        generate expression value:
                                      ...[, objectref]
                                  --> ...[, objectref], value
        if variable is Member:
          assign to member field:
                        putfield ClassName/fieldName fieldType
                                      ..., objectref, value
                                  --> ...
        if variable is Local or Parameter:
          assign to local variable n:
            if 0,1,2,3:
                        istore_<n>
                      or
                        astore_<n>
            else:
                        istore n
                      or
                        astore n
                                      ..., value
                                  --> ...

    DAGBracketAssignment: DAGVariable[DAGExpression index] = (DAGExpression value)
        load variable:
                                      ...
                                  --> ..., arrayref
        generate index expression:
                                      ..., arrayref
                                  --> ..., arrayref, index
        generate value expression:
                                      ..., arrayref, index
                                  --> ..., arrayref, index, int
        store value in arrayref[index]:
                        iastore
                                      ..., arrayref, index, int
                                  --> ...

    DAGMethodCall: (DAGExpression obj).method(DAGExpression arg1, DAGExpression arg2, ...)
        generate object expression:
                                      ...
                                  --> ..., objectref
        generate arg1 expression:
                                      ..., objectref
                                  --> ..., objectref, arg1
        generate arg2 expression:
                                      ..., objectref, arg1
                                  --> ..., objectref, arg1, arg2
                                  ...
                                  --> ..., objectref, arg1, arg2, ...
        call method:
                        invokevirtual ClassName/methodName(MethodDescriptor).

    DAGStaticCall: ClassName.method(DAGExpression arg1, DAGExpression arg2, ...)
        generate arg1 expression:
                                      ..., objectref
                                  --> ..., objectref, arg1
        generate arg2 expression:
                                      ..., objectref, arg1
                                  --> ..., objectref, arg1, arg2
                                  ...
                                  --> ..., objectref, arg1, arg2, ...
        call method:
                        invokestatic ClassName/methodName(MethodDescriptor).

    DAGVariable:
        if variable is Local or Parameter:
          if 0,1,2,3:
                        iload_<n>
                        aload_<n>
          else:
                        iload n
                        aload n
        if variable is Member:
                        aload_0
                        getfield Classname/fieldName fieldType
        if variable is This:
                        aload_0

#### Javap

    generateOperator()
    DAGAdd:
          $lhs
          $rhs
          iadd
        - istore

    generateOperator()
    DAGSub:
          $lhs
          $rhs
          isub
        - istore

    generateOperator()
    DAGMul:
          $lhs
          $rhs
          imul
        - istore

    generateOperator()
    DAGDiv:
          $lhs
          $rhs
          idiv
        - istore

    - Incorrect
    DAGLess:
          $lhs
          $rhs
          if_icmpge   A
          iconst_1
          goto        B
      A:  iconst_0
      B:> istore

    - Incorrect
    DAGAnd:
          $lhs
          ifeq        A
          $rhs
          ifeq        A
          iconst_1
          goto        B
      A:  iconst_0
      B:> istore

    - Not implemented
    DAGLength:
          $loadarray
          arraylength
        > istore

    - Not implemented
    DAGNot:
          $loadboolean
          ifne        A
          iconst_1
          goto        B
      A:  iconst_0
      B:> istore

    - Not implemented
    DAGBracket:
          $loadarray
          $loadindex
          iaload
        > istore

    - Not implemented
    DAGNewClass:
          new <class>
          dup
          invokespecial <init>:()V
        > astore

    - Not implemented
    DAGNewIntArray:
          $loadcount
          newarray
        > astore

    - Incorrect: must consider iconst, bipush, sipush and ldc.
    DAGIntegerConstant:
          iconst_<n>
        or
          bipush <constant>
        or
          sipush <constant>
        or
          ldc    <constant>

    - Not implemented
    DAGBooleanConstant:
          iconst_0
        or
          iconst_1

    ***** DAGAssignment (generateStore, generateAssignment)
    - No distinction between variable types

    DAGAssignmentMember:
          aload_0    # this
          $loadvalue
          putfield  <member>

    DAGAssignmentLocal:
          $loadvalue
        > store

    DAGBracketAssignmentMember:
          aload_0    # this
          getfield  <member>
          $loadindex
          $loadvalue
          iastore

    DAGBracketAssignmentLocal:
          $loadarray
          $loadindex
          $loadvalue
          iastore

    generateMethodCall()
    DAGMethodCall:
          $loadobjectref        # possibly aload_0
          $loadarg1
          $loadarg2
          ...
          invokevirtual <method>

    DAGStaticCall:
          $loadarg1
          $loadarg2
          ...
          invokestatic <class> <method>

    DAGMethodCall, returning:
          $loadobjectref        # possibly aload_0
          $loadarg1
          $loadarg2
          ...
          invokevirtual <method>
        > *store

    DAGStaticCall, returning:
          $loadarg1
          $loadarg2
          ...
          invokestatic <class> <method>
        > *store

    ***** DAGVariable (generateLoad, generateMemberLoad)

    generateLoad()
    DAGLocal, DAGParameter:
          aload_n
        or
          iload_n
        or
          aload <index>
        or
          iload <index>

    DAGThis:
          aload_0

    * generateMemberLoad()
    DAGMember:
          aload_0
          getfield <member descriptor>
