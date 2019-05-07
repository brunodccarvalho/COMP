Classfile /home/bruno/mieic/comp/COMP/JavapExample.class
  Last modified May 7, 2019; size 3671 bytes
  MD5 checksum 9a6ccaf9eee58a9756fc977461dc881d
  Compiled from "JavapExample.java"
class JavapExample
  minor version: 0
  major version: 55
  flags: (0x0020) ACC_SUPER
  this_class: #2                          // JavapExample
  super_class: #15                        // java/lang/Object
  interfaces: 0, fields: 7, methods: 44, attributes: 1
Constant pool:
    #1 = Methodref          #15.#94       // java/lang/Object."<init>":()V
    #2 = Class              #95           // JavapExample
    #3 = Methodref          #2.#94        // JavapExample."<init>":()V
    #4 = Integer            -1000000
    #5 = Integer            2000000
    #6 = Fieldref           #2.#96        // JavapExample.member:I
    #7 = Fieldref           #2.#97        // JavapExample.memberArray:[I
    #8 = Methodref          #2.#98        // JavapExample.getIntArray:()[I
    #9 = Methodref          #2.#99        // JavapExample.methodF:(IZI)V
   #10 = Methodref          #2.#100       // JavapExample.staticF:(IZI)V
   #11 = Methodref          #2.#101       // JavapExample.methodI:(IZI)I
   #12 = Methodref          #2.#102       // JavapExample.staticI:(IZI)I
   #13 = Methodref          #2.#103       // JavapExample.NOP_boolean:()Z
   #14 = Methodref          #2.#104       // JavapExample.NOP_int:()I
   #15 = Class              #105          // java/lang/Object
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
   #36 = Utf8               DAGAdd
   #37 = Utf8               (II)V
   #38 = Utf8               DAGSub
   #39 = Utf8               DAGMul
   #40 = Utf8               DAGDiv
   #41 = Utf8               DAGLess
   #42 = Utf8               StackMapTable
   #43 = Utf8               DAGAnd
   #44 = Utf8               (ZZ)V
   #45 = Utf8               DAGLength
   #46 = Utf8               ([I)V
   #47 = Utf8               DAGNot
   #48 = Utf8               (Z)V
   #49 = Utf8               DAGBracket
   #50 = Utf8               ([II)V
   #51 = Utf8               DAGNewClass
   #52 = Utf8               DAGNewIntArray
   #53 = Utf8               (I)V
   #54 = Utf8               DAGIntegerConstant
   #55 = Utf8               DAGBooleanConstant
   #56 = Utf8               DAGAssignmentMember
   #57 = Utf8               DAGAssignmentParameter
   #58 = Utf8               DAGAssignmentLocal
   #59 = Utf8               DAGBracketAssignmentMember
   #60 = Utf8               DAGBracketAssignmentParameter
   #61 = Utf8               ([III)V
   #62 = Utf8               DAGBracketAssignmentLocal
   #63 = Utf8               methodF
   #64 = Utf8               (IZI)V
   #65 = Utf8               staticF
   #66 = Utf8               methodI
   #67 = Utf8               (IZI)I
   #68 = Utf8               staticI
   #69 = Utf8               DAGMethodCall
   #70 = Utf8               DAGStaticCall
   #71 = Utf8               DAGMethodCallInt
   #72 = Utf8               DAGStaticCallInt
   #73 = Utf8               IfElseStatementBoolean
   #74 = Utf8               (Z)I
   #75 = Utf8               IfElseStatementLess
   #76 = Utf8               (II)I
   #77 = Utf8               IfElseStatementAnd
   #78 = Utf8               (ZZ)I
   #79 = Utf8               WhileStatementBoolean
   #80 = Utf8               WhileStatementLess
   #81 = Utf8               WhileStatementAnd
   #82 = Utf8               ReturnVoid
   #83 = Utf8               ReturnInt
   #84 = Utf8               ReturnReference
   #85 = Utf8               assignmentBoolean
   #86 = Utf8               (Z)Z
   #87 = Utf8               assignmentInt
   #88 = Utf8               (I)I
   #89 = Utf8               assignmentIntArray
   #90 = Utf8               ([I)[I
   #91 = Utf8               commonSubexpressionInt
   #92 = Utf8               SourceFile
   #93 = Utf8               JavapExample.java
   #94 = NameAndType        #26:#27       // "<init>":()V
   #95 = Utf8               JavapExample
   #96 = NameAndType        #16:#17       // member:I
   #97 = NameAndType        #18:#19       // memberArray:[I
   #98 = NameAndType        #30:#31       // getIntArray:()[I
   #99 = NameAndType        #63:#64       // methodF:(IZI)V
  #100 = NameAndType        #65:#64       // staticF:(IZI)V
  #101 = NameAndType        #66:#67       // methodI:(IZI)I
  #102 = NameAndType        #68:#67       // staticI:(IZI)I
  #103 = NameAndType        #32:#33       // NOP_boolean:()Z
  #104 = NameAndType        #34:#35       // NOP_int:()I
  #105 = Utf8               java/lang/Object
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
        line 15: 0
        line 16: 4

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
        line 19: 0
        line 20: 4

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
        line 23: 0
        line 24: 4

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
        line 27: 0
        line 28: 4

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
        line 31: 0
        line 32: 11
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
        line 35: 0
        line 36: 14
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
        line 39: 0
        line 40: 3

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
        line 43: 0
        line 44: 10
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
        line 47: 0
        line 48: 4

  void DAGNewClass();
    descriptor: ()V
    flags: (0x0000)
    Code:
      stack=2, locals=2, args_size=1
         0: new           #2                  // class JavapExample
         3: dup
         4: invokespecial #3                  // Method "<init>":()V
         7: astore_1
         8: return
      LineNumberTable:
        line 51: 0
        line 52: 8

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
        line 55: 0
        line 56: 4

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
        18: ldc           #4                  // int -1000000
        20: istore        6
        22: ldc           #5                  // int 2000000
        24: istore        7
        26: return
      LineNumberTable:
        line 59: 0
        line 60: 2
        line 61: 5
        line 62: 8
        line 63: 13
        line 64: 18
        line 65: 22
        line 66: 26

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
        line 69: 0
        line 70: 2
        line 71: 4

  void DAGAssignmentMember(int);
    descriptor: (I)V
    flags: (0x0000)
    Code:
      stack=2, locals=2, args_size=2
         0: aload_0
         1: iload_1
         2: putfield      #6                  // Field member:I
         5: return
      LineNumberTable:
        line 76: 0
        line 77: 5

  void DAGAssignmentParameter(int, int);
    descriptor: (II)V
    flags: (0x0000)
    Code:
      stack=1, locals=3, args_size=3
         0: iload_2
         1: istore_1
         2: return
      LineNumberTable:
        line 80: 0
        line 81: 2

  void DAGAssignmentLocal(int);
    descriptor: (I)V
    flags: (0x0000)
    Code:
      stack=1, locals=3, args_size=2
         0: iload_1
         1: istore_2
         2: return
      LineNumberTable:
        line 84: 0
        line 85: 2

  void DAGBracketAssignmentMember(int, int);
    descriptor: (II)V
    flags: (0x0000)
    Code:
      stack=3, locals=3, args_size=3
         0: aload_0
         1: getfield      #7                  // Field memberArray:[I
         4: iload_1
         5: iload_2
         6: iastore
         7: return
      LineNumberTable:
        line 90: 0
        line 91: 7

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
        line 94: 0
        line 95: 4

  void DAGBracketAssignmentLocal(int, int);
    descriptor: (II)V
    flags: (0x0000)
    Code:
      stack=3, locals=4, args_size=3
         0: invokestatic  #8                  // Method getIntArray:()[I
         3: astore_3
         4: aload_3
         5: iload_1
         6: iload_2
         7: iastore
         8: return
      LineNumberTable:
        line 98: 0
        line 99: 4
        line 100: 8

  void methodF(int, boolean, int);
    descriptor: (IZI)V
    flags: (0x0000)
    Code:
      stack=0, locals=4, args_size=4
         0: return
      LineNumberTable:
        line 102: 0

  static void staticF(int, boolean, int);
    descriptor: (IZI)V
    flags: (0x0008) ACC_STATIC
    Code:
      stack=0, locals=3, args_size=3
         0: return
      LineNumberTable:
        line 104: 0

  int methodI(int, boolean, int);
    descriptor: (IZI)I
    flags: (0x0000)
    Code:
      stack=1, locals=4, args_size=4
         0: iconst_0
         1: ireturn
      LineNumberTable:
        line 107: 0

  static int staticI(int, boolean, int);
    descriptor: (IZI)I
    flags: (0x0008) ACC_STATIC
    Code:
      stack=1, locals=3, args_size=3
         0: iconst_0
         1: ireturn
      LineNumberTable:
        line 111: 0

  void DAGMethodCall();
    descriptor: ()V
    flags: (0x0000)
    Code:
      stack=4, locals=1, args_size=1
         0: aload_0
         1: bipush        10
         3: iconst_1
         4: bipush        50
         6: invokevirtual #9                  // Method methodF:(IZI)V
         9: return
      LineNumberTable:
        line 115: 0
        line 116: 9

  void DAGStaticCall();
    descriptor: ()V
    flags: (0x0000)
    Code:
      stack=3, locals=1, args_size=1
         0: bipush        10
         2: iconst_1
         3: bipush        50
         5: invokestatic  #10                 // Method staticF:(IZI)V
         8: return
      LineNumberTable:
        line 119: 0
        line 120: 8

  void DAGMethodCallInt();
    descriptor: ()V
    flags: (0x0000)
    Code:
      stack=4, locals=2, args_size=1
         0: aload_0
         1: bipush        10
         3: iconst_1
         4: bipush        50
         6: invokevirtual #11                 // Method methodI:(IZI)I
         9: istore_1
        10: return
      LineNumberTable:
        line 123: 0
        line 124: 10

  void DAGStaticCallInt();
    descriptor: ()V
    flags: (0x0000)
    Code:
      stack=3, locals=2, args_size=1
         0: bipush        10
         2: iconst_1
         3: bipush        50
         5: invokestatic  #12                 // Method staticI:(IZI)I
         8: istore_1
         9: return
      LineNumberTable:
        line 127: 0
        line 128: 9

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
        line 133: 0
        line 134: 4
        line 136: 9
        line 139: 11
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
        line 145: 0
        line 146: 5
        line 148: 10
        line 151: 12
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
        line 157: 0
        line 158: 8
        line 160: 13
        line 163: 15
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
         7: invokevirtual #13                 // Method NOP_boolean:()Z
        10: istore_1
        11: iinc          2, 1
        14: goto          2
        17: iload_2
        18: ireturn
      LineNumberTable:
        line 171: 0
        line 173: 2
        line 174: 6
        line 175: 11
        line 178: 17
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
         8: invokevirtual #14                 // Method NOP_int:()I
        11: istore_1
        12: aload_0
        13: invokevirtual #14                 // Method NOP_int:()I
        16: istore_2
        17: iinc          3, 1
        20: goto          2
        23: iload_3
        24: ireturn
      LineNumberTable:
        line 182: 0
        line 184: 2
        line 185: 7
        line 186: 12
        line 187: 17
        line 190: 23
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
        11: invokevirtual #13                 // Method NOP_boolean:()Z
        14: istore_1
        15: aload_0
        16: invokevirtual #13                 // Method NOP_boolean:()Z
        19: istore_2
        20: iinc          3, 1
        23: goto          2
        26: iload_3
        27: ireturn
      LineNumberTable:
        line 194: 0
        line 196: 2
        line 197: 10
        line 198: 15
        line 199: 20
        line 202: 26
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
        line 206: 0

  int ReturnInt();
    descriptor: ()I
    flags: (0x0000)
    Code:
      stack=1, locals=1, args_size=1
         0: iconst_2
         1: ireturn
      LineNumberTable:
        line 210: 0

  int[] ReturnReference();
    descriptor: ()[I
    flags: (0x0000)
    Code:
      stack=1, locals=1, args_size=1
         0: iconst_1
         1: newarray       int
         3: areturn
      LineNumberTable:
        line 214: 0

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
        line 218: 0
        line 219: 2

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
        line 223: 0
        line 224: 2

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
        line 228: 0
        line 229: 2

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
        line 233: 0
}
SourceFile: "JavapExample.java"
