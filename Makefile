# COMP JMM Compiler Makefile

# Parser package name (use slashes)
JJT := jjt

# Compiler package name (use slashes)
COMPILER := compiler

# Test file (in folder test_files)
TEST_FILE := TestEverything.txt

# Flags
JJTREE_DEBUG :=
JJTREE_FLAGS := -track_tokens -force_la_check
JJTREE_FLAGS += -output_directory=parser/$(JJT)

JAVACC_DEBUG := -debug_parser -debug_lookahead
JAVACC_FLAGS := -output_directory=parser/$(JJT)

JAVAC_DEBUG := -g
JAVAC_FLAGS := -cp bin -d bin -Werror

COMPILER_FILES := $(shell find src/${COMPILER} -name '*.java' -type f)

.PHONY: all debug parser parser-debug load-java mkdir clean test run

all:
	@[ -f bin/jjt/jmm.class ] || ${MAKE} parser
	@echo "Compiling src/compiler ..."
	@javac $(JAVAC_FLAGS) -sourcepath src $(COMPILER_FILES)

debug:
	@[ -f bin/jjt/jmm.class ] || ${MAKE} parser
	@echo "Compiling src/compiler ..."
	@javac $(JAVAC_FLAGS) $(JAVAC_DEBUG) -sourcepath src $(COMPILER_FILES)

parser: mkdir
	jjtree $(JJTREE_FLAGS) src/$(JJT)/jmm.jjt
	javacc $(JAVACC_FLAGS) parser/$(JJT)/jmm.jj
	@${MAKE} -s load-java
	javac  $(JAVAC_FLAGS)  -sourcepath parser parser/$(JJT)/*.java

parser-debug: mkdir
	jjtree $(JJTREE_FLAGS) $(JJTREE_DEBUG) src/$(JJT)/jmm.jjt
	javacc $(JAVACC_FLAGS) $(JAVACC_DEBUG) parser/$(JJT)/jmm.jj
	@${MAKE} -s load-java
	javac  $(JAVAC_FLAGS)  $(JAVAC_DEBUG)  -sourcepath parser parser/$(JJT)/*.java

load-java:
	@cp src/jjt/SimpleNode.java parser/jjt/SimpleNode.java
	@cp src/jjt/ParseException.java parser/jjt/ParseException.java

mkdir:
	@mkdir -p bin parser

clean: mkdir
	@rm -rf bin/* parser/* compiled jjt

test:
	@java -classpath bin jmm test_files/$(TEST_FILE) || true

run:
	@java -cp bin compiler.Compiler
