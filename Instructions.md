# Java Virtual Machine Instruction Set

Suppressed: including only those types relevant to the project, plus reference arrays.

    Category 1: boolean, byte, char, short, int, float, reference, returnAddress.
    Category 2: long, double

    aaload
            ..., arrayref, index
        --> ..., objectref
      Load reference from array of reference types.

    aastore
            ..., arrayref, index, objectref
        --> ...
      Store reference into array of reference types.

    aconst_null
            ...
        --> ..., null
      Push the null object reference onto the operand stack.

    aload
      $index
            ...
        --> ..., objectref
      Load reference from local variable.
      The local variable at index must contain a reference.

    aload_n
      <n> = 0,1,2,3
            ...
        --> ..., objectref
      Load reference from local variable, small index.

    astore
      $index
            ..., objectref
        --> ...
      Store reference into local variable.

    astore_n
      <n> = 0,1,2,3
            ..., objectref
        --> ...
      Store reference into local variable, small index.

    anewarray
      $indexbyte1
      $indexbyte2
            ..., arraysize
        --> ..., arrayref
      Create new array of reference.
      The arraysize must be of type int.
      The unsigned indexbyte1 and indexbyte2 are used to construct the index
        into the run-time constant pool of the current class.
      The value of the index is 0x$indexbyte1$indexbyte2, a 16-bit integer.
      The named class or array type is resolved.
      A new array with components of that type is created from the heap.
      All components of the new array are initialized to null.

    areturn
            ..., objectref
        --> empty
      Return reference from method.

    arraylength
            ..., arrayref
        --> ..., length
      Get length of array.
      The arrayref must be of type reference to array.
      throws NullPointerException

    athrow
            ..., objectref
        --> objectref
      Throw exception or error.

    baload
            ..., arrayref, index
        --> ..., bvalue
      Load byte or boolean from array of bytes or booleans.

    bastore
            ..., arrayref, index, value
        --> ...
      Store byte or boolean into array of bytes or booleans.

    bipush
      $byte
            ...
        --> ..., int
      Push byte.
      The immediate byte is sign-extended to an int value.

    checkcast
      $indexbyte1
      $indexbyte2
            ..., objectref
        --> ..., objectref
      Checks whether object is of given type.
      The objectref must be of type reference.
      The unsigned indexbyte1 and indexbyte2 are used to construct the index
        into the run-time constant pool of the current class.
      The value of the index is 0x$indexbyte1$indexbyte2, a 16-bit integer.
      If objectref can be cast to the resolved class, array or interface type,
        then the operand stack is unchanged. Otherwise a ClassCastException
        is thrown.
      Implements C-style type cast.
      throws ClassCastException

    dup
            ..., small
        --> ..., small, small

    dup_x1
            ..., small_2, small_1
        --> ..., small_1, small_2, small_1

    dup_x2
            ..., small_3, small_2, small_1
        --> ..., small_1, small_3, small_2, small_1
            ..., large_2, small_1
            ..., small_1, large_2, small_1

    dup2
            ..., small_2, small_1
        --> ..., small_2, small_1, small_2, small_1
            ..., large
        --> ..., large, large

    dup2_x1
            ..., small_3, small_2, small_1
        --> ..., small_2, small_1, small_3, small_2, small_1
            ..., small_2, large_1
        --> ..., large_1, small_2, large_1

    dup2_x2
            ..., small_4, small_3, small_2, small_1
        --> ..., small_2, small_1, small_4, small_3, small_2, small_1
            ..., small_3, small_2, large_1
        --> ..., large_1, small_3, small_2, large_1

    getfield
      $indexbyte1
      $indexbyte2
            ..., objectref
        --> ..., value
      Fetch field from object.
      The objectref must be of type reference, non-array.
      The unsigned indexbyte1 and indexbyte2 are used to construct the index
        into the run-time constant pool of the current class.
      The value of the index is 0x$indexbyte1$indexbyte2, a 16-bit integer.
      The runtime constant pool item at that index must be a symbolic reference
        to a field, which gives the name and descriptor of the field as well as a
        symbolic reference to the class in which the field is to be found.

    getstatic
      $indexbyte1
      $indexbyte2
            ...
        --> ..., value
      Get static field from class.
      The objectref must be of type reference, non-array.
      The unsigned indexbyte1 and indexbyte2 are used to construct the index
        into the run-time constant pool of the current class.
      The value of the index is 0x$indexbyte1$indexbyte2, a 16-bit integer.
      The runtime constant pool item at that index must be a symbolic reference
        to a field, which gives the name and descriptor of the field as well as a
        symbolic reference to the class in which the field is to be found.

    goto
      $branchbyte1
      $branchbyte2
            -- no stack change
      Branch always.
      The unsigned bytes branchbyte1 and branchbyte2 are used to construct an offset.
      The value of the offset is 0x$branchbyte1$branchbyte2, a 16-bit integer.
      Execution proceeds at that offset from the address of the opcode of this
        goto instruction. The target address must be that of an opcode of an
        instruction within the method that contains this goto instruction.

    goto_w
      $branchbyte1
      $branchbyte2
      $branchbyte3
      $branchbyte4
            -- no stack change
      Branch always, wide.
      The unsigned bytes branchbyte1, branchbyte2, branchbyte3 and branchbyte4
        are used to construct an offset.
      The value of the offset is
        0x$branchbyte1$branchbyte2$branchbyte3$branchbyte4, a 32-bit integer.
      Execution proceeds at that offset from the address of the opcode of this
        goto instruction. The target address must be that of an opcode of an
        instruction within the method that contains this goto instruction.

    iadd
            ..., int1, int2
        --> ..., intsum
      Add two ints.

    isub
            ..., int1, int2
        --> ..., intsub
      Subtract two ints.

    imul
            ..., int1, int2
        --> ..., intmul
      Multiply two ints.

    idiv
            ..., int1, int2
        --> ..., intdiv
      Divide two ints.

    iaload
            ..., arrayref, index
        --> ..., int
      Load int from array of ints.

    iastore
            ..., arrayref, index, int
        --> ...
      Store int into array of ints.

    iconst_i
      <i> = m1,0,1,2,3,4,5
            ...
        --> ..., int
      Push small constant integer (-1, 0, 1, 2, 3, 4, 5) onto the operand stack.

    if_acmp<cond>
      $branchbyte1
      $branchbyte2
      <cond> = eq, ne
            ..., value1, value2
        --> ...
      Branch if reference comparison succeeds.
        eq: equals:
          succeeds iff value1 == value2.
        ne: not equals:
          succeeds iff value1 != value2.

    if_icmp<cond>
      $branchbyte1
      $branchbyte2
      <cond> = eq, ne, lt, gt, le, ge
            ..., value1, value2
        --> ...
      Branch if integer comparison succeeds.
        eq: equals:
          succeeds iff value1 == value2.
        ne: not equals:
          succeeds iff value1 != value2.
        lt: less than:
          succeeds iff value1 < value2.
        gt: greater than:
          succeeds iff value1 > value2.
        le: less than or equals:
          succeeds iff value1 <= value2.
        ge: greater than or equals:
          succeeds iff value1 >= value2.

    if<cond>
      $branchbyte1
      $branchbyte2
      <cond> = eq, ne, lt, gt, le, ge
            ..., value1
        --> ...
      Branch if integer comparison with 0 succeeds.
        eq: equals:
          succeeds iff value1 == 0.
        ne: not equals:
          succeeds iff value1 != 0.
        lt: less than:
          succeeds iff value1 < 0.
        gt: greater than:
          succeeds iff value1 > 0.
        le: less than or equals:
          succeeds iff value1 <= 0.
        ge: greater than or equals:
          succeeds iff value1 >= 0.

    ifnonnull
      $branchbyte1
      $branchbyte2
