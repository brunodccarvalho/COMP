SRC = jmm
ERROR_SRC = errors

TEST_FILE = Expression.txt
ERROR_TEST_FILE = Error.txt

JJTREE_DIR := compiled
JJTREE_DEBUG :=
JJTREE_FLAGS := -track_tokens -force_la_check
JJTREE_FLAGS += -output_directory=$(JJTREE_DIR)

JAVACC_DIR := compiled
JAVACC_DEBUG := -debug_parser -debug_lookahead
JAVACC_FLAGS := -output_directory=$(JAVACC_DIR)

JAVAC_DIR := bin
JAVAC_DEBUG := -g
JAVAC_FLAGS := -d $(JAVAC_DIR) --source-path $(JAVACC_DIR)

all: mkdir
	jjtree $(JJTREE_FLAGS) jjt/$(SRC).jjt
	javacc $(JAVACC_FLAGS) $(JJTREE_DIR)/$(SRC).jj
	cp jjt/SimpleNode.java $(JJTREE_DIR)/SimpleNode.java
	cp jjt/Node.java $(JJTREE_DIR)/Node.java
	javac  $(JAVAC_FLAGS)  $(JAVACC_DIR)/$(SRC).java

debug: mkdir
	jjtree $(JJTREE_FLAGS) $(JJTREE_DEBUG) jjt/$(SRC).jjt
	javacc $(JAVACC_FLAGS) $(JAVACC_DEBUG) $(JJTREE_DIR)/$(SRC).jj
	cp jjt/SimpleNode.java $(JJTREE_DIR)/SimpleNode.java
	cp jjt/Node.java $(JJTREE_DIR)/Node.java
	javac  $(JAVAC_FLAGS)  $(JAVAC_DEBUG)  $(JAVACC_DIR)/$(SRC).java

error: mkdir
	jjtree $(JJTREE_FLAGS) jjt/$(ERROR_SRC).jjt
	javacc $(JAVACC_FLAGS) $(JJTREE_DIR)/$(ERROR_SRC).jj
	cp jjt/SimpleNode.java $(JJTREE_DIR)/SimpleNode.java
	cp jjt/Node.java $(JJTREE_DIR)/Node.java
	javac  $(JAVAC_FLAGS)  $(JAVACC_DIR)/$(ERROR_SRC).java

mkdir:
	@mkdir -p compiled/ bin/ $(JJTREE_DIR)

clean:
	@rm -f bin/* compiled/* $(SRC).jj *.java *.class

test:
	@java -classpath bin $(SRC) test_files/$(TEST_FILE)

testerror:
	@java -classpath bin $(SRC) test_files/$(ERROR_TEST_FILE)
