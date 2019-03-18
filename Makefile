SRC = expression
ERROR_SRC = errors
TEST_FILE = Expression.txt
ERROR_TEST_FILE = Error.txt

JJTREE_DIR := compiled
JJTREE_DEBUG :=
JJTREE_FLAGS := -output_directory=$(JJTREE_DIR)

JAVACC_DIR := compiled
JAVACC_DEBUG := -debug_parser -debug_lookahead
JAVACC_FLAGS := -output_directory=$(JAVACC_DIR)

JAVAC_DIR := bin
JAVAC_DEBUG := -g
JAVAC_FLAGS := -d $(JAVAC_DIR) --source-path $(JAVACC_DIR)

all: mkdir
	jjtree $(JJTREE_FLAGS) jjt/$(SRC).jjt
	cp jjt/SimpleNode.java $(JJTREE_DIR)/SimpleNode.java
	javacc $(JAVACC_FLAGS) $(JJTREE_DIR)/$(SRC).jj
	javac  $(JAVAC_FLAGS)  $(JAVACC_DIR)/$(SRC).java

debug: mkdir
	jjtree $(JJTREE_FLAGS) $(JJTREE_DEBUG) jjt/$(SRC).jjt
	cp jjt/SimpleNode.java $(JJTREE_DIR)/SimpleNode.java
	javacc $(JAVACC_FLAGS) $(JAVACC_DEBUG) $(JJTREE_DIR)/$(SRC).jj
	javac  $(JAVAC_FLAGS)  $(JAVAC_DEBUG)  $(JAVACC_DIR)/$(SRC).java

mkdir:
	@mkdir -p compiled/ bin/ $(JJTREE_DIR)

clean:
	@rm -f bin/* compiled/* $(SRC).jj *.java *.class

test:
	@java -classpath bin $(SRC) test_files/$(TEST_FILE)

error: mkdir
	jjtree $(JJTREE_FLAGS) jjt/$(ERROR_SRC).jjt
	cp jjt/SimpleNode.java $(JJTREE_DIR)/SimpleNode.java
	javacc $(JAVACC_FLAGS) $(JJTREE_DIR)/$(ERROR_SRC).jj
	javac  $(JAVAC_FLAGS)  $(JAVACC_DIR)/$(ERROR_SRC).java

testerror:
	@java -classpath bin $(SRC) test_files/$(ERROR_TEST_FILE)
