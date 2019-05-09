Classfile /home/bruno/mieic/comp/COMP/JavapExample.class
  Last modified May 8, 2019; size 3748 bytes
  MD5 checksum 17df8d013719d44160b0efd00c3e9c9d
  Compiled from "JavapExample.java"
class JavapExample
  minor version: 0
  major version: 55
  flags: (0x0020) ACC_SUPER
  this_class: #3                          // JavapExample
  super_class: #15                        // java/lang/Object
  interfaces: 0, fields: 7, methods: 45, attributes: 1
Constant pool:
    #1 = Methodref          #15.#95       // java/lang/Object."<init>":()V
    #2 = Methodref          #3.#96        // JavapExample.NOP_int:()I
    #3 = Class              #97           // JavapExample
    #4 = Methodref          #3.#95        // JavapExample."<init>":()V
    #5 = Integer            -1000000
    #6 = Integer            2000000
    #7 = Fieldref           #3.#98        // JavapExample.member:I
    #8 = Fieldref           #3.#99        // JavapExample.memberArray:[I
    #9 = Methodref          #3.#100       // JavapExample.getIntArray:()[I
   #10 = Methodref          #3.#101       // JavapExample.methodF:(IZI)V
   #11 = Methodref          #3.#102       // JavapExample.staticF:(IZI)V
   #12 = Methodref          #3.#103       // JavapExample.methodI:(IZI)I
   #13 = Methodref          #3.#104       // JavapExample.staticI:(IZI)I
   #14 = Methodref          #3.#105       // JavapExample.NOP_boolean:()Z
   #15 = Class              #106          // java/lang/Object
   #16 = Utf8               member
   #17 = Utf8               I
   #18 = Utf8               memberArray
   #19 = Utf8               [I
   #20 = Utf8               while_boolean_b
   #21 = Utf8               Z
   #22 = Utf8               while_less_a
   #23 = Utf8               while_less_b
   #24 = Utf8               while_and_a
   #25 = Utf8               while_and_b
   #26 = Utf8               <init>
   #27 = Utf8               ()V
   #28 = Utf8               Code
   #29 = Utf8               LineNumberTable
   #30 = Utf8               getIntArray
   #31 = Utf8               ()[I
   #32 = Utf8               NOP_boolean
   #33 = Utf8               ()Z
   #34 = Utf8               NOP_int
   #35 = Utf8               ()I
   #36 = Utf8               callInts
   #37 = Utf8               DAGAdd
   #38 = Utf8               (II)V
   #39 = Utf8               DAGSub
   #40 = Utf8               DAGMul
   #41 = Utf8               DAGDiv
   #42 = Utf8               DAGLess
   #43 = Utf8               StackMapTable
   #44 = Utf8               DAGAnd
   #45 = Utf8               (ZZ)V
   #46 = Utf8               DAGLength
   #47 = Utf8               ([I)V
   #48 = Utf8               DAGNot
   #49 = Utf8               (Z)V
   #50 = Utf8               DAGBracket
   #51 = Utf8               ([II)V
   #52 = Utf8               DAGNewClass
   #53 = Utf8               DAGNewIntArray
   #54 = Utf8               (I)V
   #55 = Utf8               DAGIntegerConstant
   #56 = Utf8               DAGBooleanConstant
   #57 = Utf8               DAGAssignmentMember
   #58 = Utf8               DAGAssignmentParameter
   #59 = Utf8               DAGAssignmentLocal
   #60 = Utf8               DAGBracketAssignmentMember
   #61 = Utf8               DAGBracketAssignmentParameter
   #62 = Utf8               ([III)V
   #63 = Utf8               DAGBracketAssignmentLocal
   #64 = Utf8               methodF
   #65 = Utf8               (IZI)V
   #66 = Utf8               staticF
   #67 = Utf8               methodI
   #68 = Utf8               (IZI)I
   #69 = Utf8               staticI
   #70 = Utf8               DAGMethodCall
   #71 = Utf8               DAGStaticCall
   #72 = Utf8               DAGMethodCallInt
   #73 = Utf8               DAGStaticCallInt
   #74 = Utf8               IfElseStatementBoolean
   #75 = Utf8               (Z)I
   #76 = Utf8               IfElseStatementLess
   #77 = Utf8               (II)I
   #78 = Utf8               IfElseStatementAnd
   #79 = Utf8               (ZZ)I
   #80 = Utf8               WhileStatementBoolean
   #81 = Utf8               WhileStatementLess
   #82 = Utf8               WhileStatementAnd
   #83 = Utf8               ReturnVoid
   #84 = Utf8               ReturnInt
   #85 = Utf8               ReturnReference
   #86 = Utf8               assignmentBoolean
   #87 = Utf8               (Z)Z
   #88 = Utf8               assignmentInt
   #89 = Utf8               (I)I
   #90 = Utf8               assignmentIntArray
   #91 = Utf8               ([I)[I
   #92 = Utf8               commonSubexpressionInt
   #93 = Utf8               SourceFile
   #94 = Utf8               JavapExample.java
   #95 = NameAndType        #26:#27       // "<init>":()V
   #96 = NameAndType        #34:#35       // NOP_int:()I
   #97 = Utf8               JavapExample
   #98 = NameAndType        #16:#17       // member:I
   #99 = NameAndType        #18:#19       // memberArray:[I
  #100 = NameAndType        #30:#31       // getIntArray:()[I
  #101 = NameAndType        #64:#65       // methodF:(IZI)V
  #102 = NameAndType        #66:#65       // staticF:(IZI)V
  #103 = NameAndType        #67:#68       // methodI:(IZI)I
  #104 = NameAndType        #69:#68       // staticI:(IZI)I
  #105 = NameAndType        #32:#33       // NOP_boolean:()Z
  #106 = Utf8               java/lang/Object
{
  int member;
    descriptor: I
    flags: (0x0000)

  int[] memberArray;
    descriptor: [I
    flags: (0x0000)

  boolean while_boolean_b;
    descriptor: Z
    flags: (0x0000)

  int while_less_a;
    descriptor: I
    flags: (0x0000)

  int while_less_b;
    descriptor: I
    flags: (0x0000)

  boolean while_and_a;
    descriptor: Z
    flags: (0x0000)

  boolean while_and_b;
    descriptor: Z
    flags: (0x0000)

  JavapExample();
    descriptor: ()V
    flags: (0x0000)
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 1: 0

  static int[] getIntArray();
    descriptor: ()[I
    flags: (0x0008) ACC_STATIC
    Code:
      stack=1, locals=0, args_size=0
         0: aconst_null
         1: areturn
      LineNumberTable:
        line 3: 0

  boolean NOP_boolean();
    descriptor: ()Z
    flags: (0x0000)
    Code:
      stack=1, locals=1, args_size=1
         0: iconst_1
         1: ireturn
      LineNumberTable:
        line 7: 0

  int NOP_int();
    descriptor: ()I
    flags: (0x0000)
    Code:
      stack=1, locals=1, args_size=1
         0: iconst_2
         1: ireturn
      LineNumberTable:
        line 11: 0

  void callInts();
    descriptor: ()V
    flags: (0x0000)
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokevirtual #2                  // Method NOP_int:()I
         4: pop
         5: aload_0
         6: invokevirtual #2                  // Method NOP_int:()I
         9: pop
        10: aload_0
        11: invokevirtual #2                  // Method NOP_int:()I
        14: pop
        15: return
      LineNumberTable:
        line 15: 0
        line 16: 5
        line 17: 10
        line 18: 15

  void DAGAdd(int, int);
    descriptor: (II)V
    flags: (0x0000)
    Code:
      stack=2, locals=4, args_size=3
         0: iload_1
         1: iload_2
         2: iadd
         3: istore_3
         4: return
      LineNumberTable:
        line 21: 0
        line 22: 4

  void DAGSub(int, int);
    descriptor: (II)V
    flags: (0x0000)
    Code:
      stack=2, locals=4, args_size=3
         0: iload_1
         1: iload_2
         2: isub
         3: istore_3
         4: return
      LineNumberTable:
        line 25: 0
        line 26: 4

  void DAGMul(int, int);
    descriptor: (II)V
    flags: (0x0000)
    Code:
      stack=2, locals=4, args_size=3
         0: iload_1
         1: iload_2
         2: imul
         3: istore_3
         4: return
      LineNumberTable:
        line 29: 0
        line 30: 4

  void DAGDiv(int, int);
    descriptor: (II)V
    flags: (0x0000)
    Code:
      stack=2, locals=4, args_size=3
         0: iload_1
         1: iload_2
         2: idiv
         3: istore_3
         4: return
      LineNumberTable:
        line 33: 0
        line 34: 4

  void DAGLess(int, int);
    descriptor: (II)V
    flags: (0x0000)
    Code:
      stack=2, locals=4, args_size=3
         0: iload_1
         1: iload_2
         2: if_icmpge     9
         5: iconst_1
         6: goto          10
         9: iconst_0
        10: istore_3
        11: return
      LineNumberTable:
        line 37: 0
        line 38: 11
      StackMapTable: number_of_entries = 2
        frame_type = 9 /* same */
        frame_type = 64 /* same_locals_1_stack_item */
          stack = [ int ]

  void DAGAnd(boolean, boolean);
    descriptor: (ZZ)V
    flags: (0x0000)
    Code:
      stack=1, locals=4, args_size=3
         0: iload_1
         1: ifeq          12
         4: iload_2
         5: ifeq          12
         8: iconst_1
         9: goto          13
        12: iconst_0
        13: istore_3
        14: return
      LineNumberTable:
        line 41: 0
        line 42: 14
      StackMapTable: number_of_entries = 2
        frame_type = 12 /* same */
        frame_type = 64 /* same_locals_1_stack_item */
          stack = [ int ]

  void DAGLength(int[]);
    descriptor: ([I)V
    flags: (0x0000)
    Code:
      stack=1, locals=3, args_size=2
         0: aload_1
         1: arraylength
         2: istore_2
         3: return
      LineNumberTable:
        line 45: 0
        line 46: 3

  void DAGNot(boolean);
    descriptor: (Z)V
    flags: (0x0000)
    Code:
      stack=1, locals=3, args_size=2
         0: iload_1
         1: ifne          8
         4: iconst_1
         5: goto          9
         8: iconst_0
         9: istore_2
        10: return
      LineNumberTable:
        line 49: 0
        line 50: 10
      StackMapTable: number_of_entries = 2
        frame_type = 8 /* same */
        frame_type = 64 /* same_locals_1_stack_item */
          stack = [ int ]

  void DAGBracket(int[], int);
    descriptor: ([II)V
    flags: (0x0000)
    Code:
      stack=2, locals=4, args_size=3
         0: aload_1
         1: iload_2
         2: iaload
         3: istore_3
         4: return
      LineNumberTable:
        line 53: 0
        line 54: 4

  void DAGNewClass();
    descriptor: ()V
    flags: (0x0000)
    Code:
      stack=2, locals=2, args_size=1
         0: new           #3                  // class JavapExample
         3: dup
         4: invokespecial #4                  // Method "<init>":()V
         7: astore_1
         8: return
      LineNumberTable:
        line 57: 0
        line 58: 8

  void DAGNewIntArray(int);
    descriptor: (I)V
    flags: (0x0000)
    Code:
      stack=1, locals=3, args_size=2
         0: iload_1
         1: newarray       int
         3: astore_2
         4: return
      LineNumberTable:
        line 61: 0
        line 62: 4

  void DAGIntegerConstant();
    descriptor: ()V
    flags: (0x0000)
    Code:
      stack=1, locals=8, args_size=1
         0: iconst_3
         1: istore_1
         2: bipush        -78
         4: istore_2
         5: bipush        110
         7: istore_3
         8: sipush        -25000
        11: istore        4
        13: sipush        30000
        16: istore        5
        18: ldc           #5                  // int -1000000
        20: istore        6
        22: ldc           #6                  // int 2000000
        24: istore        7
        26: return
      LineNumberTable:
        line 65: 0
        line 66: 2
        line 67: 5
        line 68: 8
        line 69: 13
        line 70: 18
        line 71: 22
        line 72: 26

  void DAGBooleanConstant();
    descriptor: ()V
    flags: (0x0000)
    Code:
      stack=1, locals=3, args_size=1
         0: iconst_0
         1: istore_1
         2: iconst_1
         3: istore_2
         4: return
      LineNumberTable:
        line 75: 0
        line 76: 2
        line 77: 4

  void DAGAssignmentMember(int);
    descriptor: (I)V
    flags: (0x0000)
    Code:
      stack=2, locals=2, args_size=2
         0: aload_0
         1: iload_1
         2: putfield      #7                  // Field member:I
         5: return
      LineNumberTable:
        line 82: 0
        line 83: 5

  void DAGAssignmentParameter(int, int);
    descriptor: (II)V
    flags: (0x0000)
    Code:
      stack=1, locals=3, args_size=3
         0: iload_2
         1: istore_1
         2: return
      LineNumberTable:
        line 86: 0
        line 87: 2

  void DAGAssignmentLocal(int);
    descriptor: (I)V
    flags: (0x0000)
    Code:
      stack=1, locals=3, args_size=2
         0: iload_1
         1: istore_2
         2: return
      LineNumberTable:
        line 90: 0
        line 91: 2

  void DAGBracketAssignmentMember(int, int);
    descriptor: (II)V
    flags: (0x0000)
    Code:
      stack=3, locals=3, args_size=3
         0: aload_0
         1: getfield      #8                  // Field memberArray:[I
         4: iload_1
         5: iload_2
         6: iastore
         7: return
      LineNumberTable:
        line 96: 0
        line 97: 7

  void DAGBracketAssignmentParameter(int[], int, int);
    descriptor: ([III)V
    flags: (0x0000)
    Code:
      stack=3, locals=4, args_size=4
         0: aload_1
         1: iload_2
         2: iload_3
         3: iastore
         4: return
      LineNumberTable:
        line 100: 0
        line 101: 4

  void DAGBracketAssignmentLocal(int, int);
    descriptor: (II)V
    flags: (0x0000)
    Code:
      stack=3, locals=4, args_size=3
         0: invokestatic  #9                  // Method getIntArray:()[I
         3: astore_3
         4: aload_3
         5: iload_1
         6: iload_2
         7: iastore
         8: return
      LineNumberTable:
        line 104: 0
        line 105: 4
        line 106: 8

  void methodF(int, boolean, int);
    descriptor: (IZI)V
    flags: (0x0000)
    Code:
      stack=0, locals=4, args_size=4
         0: return
      LineNumberTable:
        line 108: 0

  static void staticF(int, boolean, int);
    descriptor: (IZI)V
    flags: (0x0008) ACC_STATIC
    Code:
      stack=0, locals=3, args_size=3
         0: return
      LineNumberTable:
        line 110: 0

  int methodI(int, boolean, int);
    descriptor: (IZI)I
    flags: (0x0000)
    Code:
      stack=1, locals=4, args_size=4
         0: iconst_0
         1: ireturn
      LineNumberTable:
        line 113: 0

  static int staticI(int, boolean, int);
    descriptor: (IZI)I
    flags: (0x0008) ACC_STATIC
    Code:
      stack=1, locals=3, args_size=3
         0: iconst_0
         1: ireturn
      LineNumberTable:
        line 117: 0

  void DAGMethodCall();
    descriptor: ()V
    flags: (0x0000)
    Code:
      stack=4, locals=1, args_size=1
         0: aload_0
         1: bipush        10
         3: iconst_1
         4: bipush        50
         6: invokevirtual #10                 // Method methodF:(IZI)V
         9: return
      LineNumberTable:
        line 121: 0
        line 122: 9

  void DAGStaticCall();
    descriptor: ()V
    flags: (0x0000)
    Code:
      stack=3, locals=1, args_size=1
         0: bipush        10
         2: iconst_1
         3: bipush        50
         5: invokestatic  #11                 // Method staticF:(IZI)V
         8: return
      LineNumberTable:
        line 125: 0
        line 126: 8

  void DAGMethodCallInt();
    descriptor: ()V
    flags: (0x0000)
    Code:
      stack=4, locals=2, args_size=1
         0: aload_0
         1: bipush        10
         3: iconst_1
         4: bipush        50
         6: invokevirtual #12                 // Method methodI:(IZI)I
         9: istore_1
        10: return
      LineNumberTable:
        line 129: 0
        line 130: 10

  void DAGStaticCallInt();
    descriptor: ()V
    flags: (0x0000)
    Code:
      stack=3, locals=2, args_size=1
         0: bipush        10
         2: iconst_1
         3: bipush        50
         5: invokestatic  #13                 // Method staticI:(IZI)I
         8: istore_1
         9: return
      LineNumberTable:
        line 133: 0
        line 134: 9

  int IfElseStatementBoolean(boolean);
    descriptor: (Z)I
    flags: (0x0000)
    Code:
      stack=1, locals=3, args_size=2
         0: iload_1
         1: ifeq          9
         4: iconst_1
         5: istore_2
         6: goto          11
         9: iconst_2
        10: istore_2
        11: iload_2
        12: ireturn
      LineNumberTable:
        line 139: 0
        line 140: 4
        line 142: 9
        line 145: 11
      StackMapTable: number_of_entries = 2
        frame_type = 9 /* same */
        frame_type = 252 /* append */
          offset_delta = 1
          locals = [ int ]

  int IfElseStatementLess(int, int);
    descriptor: (II)I
    flags: (0x0000)
    Code:
      stack=2, locals=4, args_size=3
         0: iload_1
         1: iload_2
         2: if_icmpge     10
         5: iconst_1
         6: istore_3
         7: goto          12
        10: iconst_2
        11: istore_3
        12: iload_3
        13: ireturn
      LineNumberTable:
        line 151: 0
        line 152: 5
        line 154: 10
        line 157: 12
      StackMapTable: number_of_entries = 2
        frame_type = 10 /* same */
        frame_type = 252 /* append */
          offset_delta = 1
          locals = [ int ]

  int IfElseStatementAnd(boolean, boolean);
    descriptor: (ZZ)I
    flags: (0x0000)
    Code:
      stack=1, locals=4, args_size=3
         0: iload_1
         1: ifeq          13
         4: iload_2
         5: ifeq          13
         8: iconst_1
         9: istore_3
        10: goto          15
        13: iconst_2
        14: istore_3
        15: iload_3
        16: ireturn
      LineNumberTable:
        line 163: 0
        line 164: 8
        line 166: 13
        line 169: 15
      StackMapTable: number_of_entries = 2
        frame_type = 13 /* same */
        frame_type = 252 /* append */
          offset_delta = 1
          locals = [ int ]

  int WhileStatementBoolean(boolean);
    descriptor: (Z)I
    flags: (0x0000)
    Code:
      stack=1, locals=3, args_size=2
         0: iconst_0
         1: istore_2
         2: iload_1
         3: ifeq          17
         6: aload_0
         7: invokevirtual #14                 // Method NOP_boolean:()Z
        10: istore_1
        11: iinc          2, 1
        14: goto          2
        17: iload_2
        18: ireturn
      LineNumberTable:
        line 177: 0
        line 179: 2
        line 180: 6
        line 181: 11
        line 184: 17
      StackMapTable: number_of_entries = 2
        frame_type = 252 /* append */
          offset_delta = 2
          locals = [ int ]
        frame_type = 14 /* same */

  int WhileStatementLess(int, int);
    descriptor: (II)I
    flags: (0x0000)
    Code:
      stack=2, locals=4, args_size=3
         0: iconst_0
         1: istore_3
         2: iload_1
         3: iload_2
         4: if_icmpge     23
         7: aload_0
         8: invokevirtual #2                  // Method NOP_int:()I
        11: istore_1
        12: aload_0
        13: invokevirtual #2                  // Method NOP_int:()I
        16: istore_2
        17: iinc          3, 1
        20: goto          2
        23: iload_3
        24: ireturn
      LineNumberTable:
        line 188: 0
        line 190: 2
        line 191: 7
        line 192: 12
        line 193: 17
        line 196: 23
      StackMapTable: number_of_entries = 2
        frame_type = 252 /* append */
          offset_delta = 2
          locals = [ int ]
        frame_type = 20 /* same */

  int WhileStatementAnd(boolean, boolean);
    descriptor: (ZZ)I
    flags: (0x0000)
    Code:
      stack=1, locals=4, args_size=3
         0: iconst_0
         1: istore_3
         2: iload_1
         3: ifeq          26
         6: iload_2
         7: ifeq          26
        10: aload_0
        11: invokevirtual #14                 // Method NOP_boolean:()Z
        14: istore_1
        15: aload_0
        16: invokevirtual #14                 // Method NOP_boolean:()Z
        19: istore_2
        20: iinc          3, 1
        23: goto          2
        26: iload_3
        27: ireturn
      LineNumberTable:
        line 200: 0
        line 202: 2
        line 203: 10
        line 204: 15
        line 205: 20
        line 208: 26
      StackMapTable: number_of_entries = 2
        frame_type = 252 /* append */
          offset_delta = 2
          locals = [ int ]
        frame_type = 23 /* same */

  void ReturnVoid();
    descriptor: ()V
    flags: (0x0000)
    Code:
      stack=0, locals=1, args_size=1
         0: return
      LineNumberTable:
        line 212: 0

  int ReturnInt();
    descriptor: ()I
    flags: (0x0000)
    Code:
      stack=1, locals=1, args_size=1
         0: iconst_2
         1: ireturn
      LineNumberTable:
        line 216: 0

  int[] ReturnReference();
    descriptor: ()[I
    flags: (0x0000)
    Code:
      stack=1, locals=1, args_size=1
         0: iconst_1
         1: newarray       int
         3: areturn
      LineNumberTable:
        line 220: 0

  boolean assignmentBoolean(boolean);
    descriptor: (Z)Z
    flags: (0x0000)
    Code:
      stack=1, locals=3, args_size=2
         0: iload_1
         1: istore_2
         2: iload_2
         3: ireturn
      LineNumberTable:
        line 224: 0
        line 225: 2

  int assignmentInt(int);
    descriptor: (I)I
    flags: (0x0000)
    Code:
      stack=1, locals=3, args_size=2
         0: iload_1
         1: istore_2
         2: iload_2
         3: ireturn
      LineNumberTable:
        line 229: 0
        line 230: 2

  int[] assignmentIntArray(int[]);
    descriptor: ([I)[I
    flags: (0x0000)
    Code:
      stack=1, locals=3, args_size=2
         0: aload_1
         1: astore_2
         2: aload_2
         3: areturn
      LineNumberTable:
        line 234: 0
        line 235: 2

  int commonSubexpressionInt(int, int);
    descriptor: (II)I
    flags: (0x0000)
    Code:
      stack=3, locals=3, args_size=3
         0: iload_1
         1: iload_2
         2: imul
         3: iconst_1
         4: iadd
         5: iload_1
         6: iload_2
         7: imul
         8: iconst_1
         9: iadd
        10: iadd
        11: ireturn
      LineNumberTable:
        line 239: 0
}
SourceFile: "JavapExample.java"
