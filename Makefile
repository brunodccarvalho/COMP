# COMP JMM Compiler Makefile

# Parser package name (use slashes)
JJT := jjt

# Compiler package name (use slashes)
COMPILER := compiler

# Test file (in folder test_files)
TEST_FILE := code_generator/Dog.java

# Jasmin file (to see how javac writes bytecode).
JASMIN_FILE := JavapExample.java

# Flags
JJTREE_DEBUG :=
JJTREE_FLAGS := -track_tokens -force_la_check
JJTREE_FLAGS += -output_directory=parser/$(JJT)

JAVACC_DEBUG := -debug_parser -debug_lookahead
JAVACC_FLAGS := -output_directory=parser/$(JJT)

JAVAC_DEBUG := -g
JAVAC_FLAGS := -cp bin -d bin -Werror -sourcepath src

JAVA_DEBUG :=
JAVA_FLAGS := -cp bin -ea

COMPILER_FILES := $(shell find src/${COMPILER} -name '*.java' -type f)

.PHONY: all debug parser parser-debug load-java mkdir clean test run

all: parser
	@echo "Compiling src/compiler ..."
	@javac $(JAVAC_FLAGS) $(COMPILER_FILES)

debug: parser-debug
	@echo "Compiling src/compiler ..."
	@javac $(JAVAC_FLAGS) $(JAVAC_DEBUG) $(COMPILER_FILES)

parser: mkdir
	@jjtree $(JJTREE_FLAGS) jmm/$(JJT)/jmm.jjt
	@javacc $(JAVACC_FLAGS) parser/$(JJT)/jmm.jj
	@${MAKE} -s load-java
	@javac  $(JAVAC_FLAGS) src/$(JJT)/*.java

parser-debug: mkdir
	@jjtree $(JJTREE_FLAGS) $(JJTREE_DEBUG) jmm/$(JJT)/jmm.jjt
	@javacc $(JAVACC_FLAGS) $(JAVACC_DEBUG) parser/$(JJT)/jmm.jj
	@${MAKE} -s load-java
	@javac  $(JAVAC_FLAGS)  $(JAVAC_DEBUG) src/$(JJT)/*.java

load-java:
	@cp jmm/jjt/SimpleNode.java parser/jjt/SimpleNode.java
	@cp jmm/jjt/ParseException.java parser/jjt/ParseException.java
	@cp -t src/jjt/ parser/jjt/*.java

mkdir:
	@mkdir -p bin parser

clean: mkdir
	@rm -rf bin/* parser/* compiled jjt

test:
	@java -Xdiag $(JAVA_DEBUG) $(JAVA_FLAGS) compiler.Compiler test_files/$(TEST_FILE) || true

parser-test:
	@java -Xdiag $(JAVA_DEBUG) $(JAVA_FLAGS) jjt.jmm test_files/$(TEST_FILE) || true

jasmin:
	@javac $(JASMIN_FILE)
	@javap -verbose -cp . -s $(JASMIN_FILE:.java=) > $(JASMIN_FILE:.java=.j)
	@rm $(JASMIN_FILE:.java=.class)
