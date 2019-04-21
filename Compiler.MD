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
verify there are no conflicts. For member methods these cannot be in
conflict with the implicit member variables.

### 3. Function return statement

For the member methods, read the return statement of the function.
The return type should be deduceable by now. It should agree with the
declared function return type or be convertible to it.

### 4. Function statements

Parse the function statements in depth-first order.

## Name Resolution
---

#### Member variable declaration

1. PrimitiveType Identifier;
    * Validate Identifier.
        1. Own class's data members table
        2. Class names table
2. ClassType Identifier;
    * Resolve or deduce ClassType first.
        1. Class names table
    * Validate Identifier second.

#### Parameter variable declaration, member method

1. PrimitiveType Identifier;
    * Validate Identifier.
        1. Own method's parameters table
        2. Own class's data members table
        3. Class names table
2. ClassType Identifier;
    * Resolve or deduce ClassType first.
        1. Class names table
    * Validate Identifier second.

#### Local variable declaration, member method
1. PrimitiveType Identifier;
    * Validate Identifier;
        1. Own method's local variables table
        2. Own method's parameters table
        3. Own class's data members table
        4. Class names table
2. ClassType Identifier;
    * Resolve or deduce ClassType first.
        1. Class names table
    * Validate Identifier second.

#### Local variable declaration, static method

1. PrimitiveType Identifier;
    * Validate Identifier;
        1. Own method's local variables table
        2. Own method's parameters table
        3. Class names table
2. ClassType Identifier;
    * Resolve or deduce ClassType first.
        1. Class names table
    * Validate Identifier second.

#### Return type declaration, member method

1. PrimitiveType
    * Accept and do nothing
2. ClassType
    * Resolve or deduce ClassType first.
        1. Class names table

#### Term, member method

1. Identifier
    * Resolve Identifier.
        1. Own method's local variables table
        2. Own method's parameters table
        3. Own class's data members table
    * If unsuccessful this is an error -- output a diagnostic.
        * Check if the identifier is in the class names table, for misuse of a type.

#### Term, static method

1. Identifier
    * Resolve Identifier.
        1. Own method's local variables table
        2. Own method's parameters table
    * If unsuccessful this is an error -- output a diagnostic.
        * Check if the identifier is in the class names table, for misuse of a type.

#### Bracket

1. ExpressionA[ExpressionB]
    * ExpressionA must evaluate to an array, i.e. some type A[].
        * If the evaluation type cannot be deduced, it is assumed to be A[]
    * ExpressionB must evaluate to an integer.
        * If the evaluation type cannot be deduced, it is assumed to be A[]
    * The Bracket evaluates to type A.

#### Call

1. ExpressionA.IdentifierB(ExpressionCs...)
    * ExpressionA must evaluate to a class instance or class type
    1.1. If ExpressionA evaluates to an instance X of class A
        * Then class A should have a member a static method with name IdentifierB.
    1.2. If ExpressionA evaluates to a class type A
        * Then class A should have a static method with name IdentifierB.
    * The Call evaluates to the return type of the called method.

## Descriptors

Sketch & ideas

enum of descriptor types:
int, bool, int[], class.

A class descriptor may be populated if the class's contents (data members and methods) are known
upfront, our it may need to be populatable as methods are 'deduced' from the source code
provided. Suppose we are compiling class A and we find code like

B b(1.0, true);
b.increment(3.5).decrement(2);

but we know nothing of class B. Then we should be able to infer (making a bold assumption) that
class B has a constructor that takes a double and a boolean, and also a method called increment
that takes a double. The return value of this increment call shall be some class C that has a
method called decrement that takes an integer. We __cannot__ later find that another call to
method increment() with a single argument of type double that returns, say, an integer, as that
is incompatible with the above statement.
