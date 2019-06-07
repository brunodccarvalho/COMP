# Compiler of the Java-- language to Java Bytecodes [Group 62]

### Project Evaluation

All the grades listed below are provided on a scale of 1 to 20.

|      | Name           | Number    | Self Assessment | Grade 1            | Grade 2            | Grade 3            | Grade 4            | Contribution (%) |
| ---- | -------------- | --------- | --------------- | ------------------ | ------------------ | ------------------ | ------------------ | ---------------- |
| 1    | Bruno Carvalho | 201606517 | 18              | :heavy_minus_sign: | 18                 | 18                 | 18                 | 25               |
| 2    | João Lima      | 201605314 | 18              | 18                 | :heavy_minus_sign: | 18                 | 18                 | 25               |
| 3    | João Maduro    | 201605219 | 18              | 18                 | 18                 | :heavy_minus_sign: | 18                 | 25               |
| 4    | Sofia Martins  | 201606033 | 18              | 18                 | 18                 | 18                 | :heavy_minus_sign: | 25               |

Given the features added to the compiler (which are detailed in the following sections), the group believes that a fair global grade would be __18__ out of 20.



## 1. Summary

The intention of this assignment was to develop a compiler, named _jmm_, which is able to translate Java-- programs into java _bytecodes_. The compiler follows a well defined compilation flow, which includes: lexical analysis (using an __LL(1)__ parser), syntactic analysis, semantic analysis and code generation. Among these stages, it includes:

- Error treatment and recover mechanisms
- Generation of a Syntax Tree (_Abstract Syntax Tree_)
- Generation of a DAG (_Directed Acyclic Graph_)
- Generation of java _bytecodes_ 



## 2. Execute

The execute the compiler, use the following commands:

```console
java –jar jmm.jar [-r=<num>] [-o] <input_file.jmm>
```



## 3. Dealing with Syntactic Errors

The compiler is able to skip a predefined number of errors, using the approach suggested on the [project's proposal](https://moodle.up.pt/pluginfile.php/223759/mod_resource/content/7/Projecto/jmm-CompilerProject2019.pdf). Using the _numberRecoveries_ variable (in the file _jmm.jjt_), the compiler is able to report _numberRecoveries_ errors, so that the programmer can then proceed with their correction. This is done by skipping blocks of code whenever a new error is found, and incrementing a counter. 

## 4. Semantic Analysis

The compiler implements the following semantic rules:

- Check if the return value of a function is ever initialized.

- Check if variables are assigned to other variables with compatible types.

- Check if the function called is compatible with any function (that is, a function having the same signature - number of arguments, as well as the type of those arguments).

- Check if the return value of a function can be assigned to a variable.

- Check if a variable is valid within a given scope.

- Check if the return value of a function can be used in an expression.

- Check if a variable is not defined more than one time.

- Assumes the return value of a function it doesn't know to the variable it is beeing assigned or assumes it is void if not being assigned to anything

  

## 5. Intermediate Representations (IRs)

The intermediate representation is being delivered by both the Syntax Tree (_Abstract Syntax Tree_) and the DAG (_Directed Acyclic Graph_). This representation is made after both the lexical and syntax are complete. Also, the IR help us structure the Java-- code in something more simpler and manageable. It will also help us in the optimizations of the code generation part of the project.



## 6. Code Generation



The code generation is performed using as an input a DAG (_Directed Acyclic Graph_), which is generated from the AST (_Abstract Syntax Tree_).  Then, the DAG is transversed starting from its root. Each DAG node is then matched with a JVM instruction. This instructions are already defined by the compiler, but are incomplete, having _?_ operators to mark a value that is expected by that same instruction. Each of the values are then provided in order, so that they can replace the _?_ operators, and the instructions outputted to the _class_ file.

## 7. Overview

The group was able to achieve the expected compiler in this project:
1. Developed a parser for Java-- using JavaCC and taking as starting point the Java-- grammar furnished (using LL(1));
2. Included error treatment and recovery mechanisms;
3. Proceeded with the specification of the AST;
4. Included the necessary symbol tables;
5. Semantic Analysis;
6. Generated JVM code accepted by jasmin corresponding to the invocation of functions in Java--;
7. Generated JVM code accepted by jasmin for arithmetic expressions;
8. Generated JVM code accepted by jasmin for conditional instructions (if and if-else);
9. Generated JVM code accepted by jasmin for loops;
10. Generated JVM code accepted by jasmin to deal with arrays.
11. Completed the compiler and test it using a set of Java-- classes;
12. Proceeded with the optimizations related to the code generation, related to the register allocation (“-r” option) and the optimizations related to the “-o” option.

This were the suggested stages for the compiler and they were all applied in this project.


## 8. Task Distribution

The tasks were well distributed betweed all the peers in this work. All of us had a change to work in every topic. The work was passed around to keep everyone interested and to be able to help the classmate. It should be also noted that everyone impacted the work the same way and help to provide a stable and healthy group environment.

## 9. Pros

The project was well rounded and distributed along it's parts, all having their more troubelling counterparts. This project gave us a better insight vision of how a compiller works and processes the information. It should also be taken in account the amount of new information learnt over the course of the semester to build this compiler.

## 10. Cons

The language has a very limited syntax. If included more like the programming language C it whould leave more option of implematation but whould also make the project more difficult. A good balance between a feasible work and a troubling one should be taken in consideration.