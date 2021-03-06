# COMP JMM Compiler Makefile

# Parser package name (use slashes)
JJT := jjt

# Compiler package name (use slashes)
COMPILER := compiler

# Test file (in folder test_files)
TEST_FILE := Quicksort.jmm

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
JAR_FLAGS := cvfe
ENTRY_POINT := compiler.Compiler
OUTPUT_JAR := compiler.jar

COMPILER_FILES := $(shell find src/${COMPILER} -name '*.java' -type f)
CLASS_FILES := -C bin .

.PHONY: all debug parser parser-debug load-java mkdir clean test run

all: parser
	@echo "Compiling src/compiler ..."
	@javac $(JAVAC_FLAGS) $(COMPILER_FILES)

jar: all
	@jar $(JAR_FLAGS) $(OUTPUT_JAR) $(ENTRY_POINT) $(CLASS_FILES)

testjar: jar
	@mv compiler.jar test_files/compiler.jar

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
	@rm -rf bin/* parser/* compiled jjt compiler.jar

test:
	@clear
	@java -Xdiag $(JAVA_DEBUG) $(JAVA_FLAGS) compiler.Compiler test_files/$(TEST_FILE) || true

parser-test:
	@clear
	@java -Xdiag $(JAVA_DEBUG) $(JAVA_FLAGS) jjt.jmm test_files/$(TEST_FILE) || true

jasmin:
	@javac $(JASMIN_FILE)
	@javap -verbose -cp . -s $(JASMIN_FILE:.java=) > $(JASMIN_FILE:.java=.j)
	@rm $(JASMIN_FILE:.java=.class)

jvm:
	@java -jar jasmin_files/jasmin.jar bin/codeGenerator/$(basename $(TEST_FILE)).j -d jasmin_files
	@java -cp jasmin_files $(basename $(TEST_FILE))
