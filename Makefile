SRC = jmm

TEST_FILE = Expression.txt
ERROR_TEST_FILE = Error.txt

NUMBER_RECOVERIES = 3

JJTREE_DIR := compiled
JJTREE_DEBUG :=
JJTREE_FLAGS := -track_tokens -force_la_check
JJTREE_FLAGS += -output_directory=$(JJTREE_DIR)

JAVACC_DIR := compiled
JAVACC_DEBUG := -debug_parser -debug_lookahead
JAVACC_FLAGS := -output_directory=$(JAVACC_DIR)

JAVAC_DIR := bin
JAVAC_DEBUG := -g
JAVAC_FLAGS := -d $(JAVAC_DIR) -sourcepath $(JAVACC_DIR)

all: mkdir
	jjtree $(JJTREE_FLAGS) jjt/$(SRC).jjt
	javacc $(JAVACC_FLAGS) $(JJTREE_DIR)/$(SRC).jj
	$(MAKE) loadJava
	javac  $(JAVAC_FLAGS)  $(JAVACC_DIR)/$(SRC).java

debug: mkdir
	jjtree $(JJTREE_FLAGS) $(JJTREE_DEBUG) jjt/$(SRC).jjt
	javacc $(JAVACC_FLAGS) $(JAVACC_DEBUG) $(JJTREE_DIR)/$(SRC).jj
	$(MAKE) loadJava
	javac  $(JAVAC_FLAGS)  $(JAVAC_DEBUG)  $(JAVACC_DIR)/$(SRC).java

loadJava:
	cp jjt/SimpleNode.java $(JJTREE_DIR)/SimpleNode.java
	cp jjt/ParseException.java $(JJTREE_DIR)/ParseException.java

mkdir:
	@mkdir -p $(JAVACC_DIR) $(JAVAC_DIR) $(JJTREE_DIR)

clean:
	@rm -f $(JAVAC_DIR)/* $(JAVACC_DIR)/* $(JJTREE_DIR)/* $(SRC).jj *.java *.class

test:
	@java -classpath $(JAVAC_DIR) $(SRC) test_files/$(TEST_FILE) $(NUMBER_RECOVERIES) || true

testerror:
	@java -classpath $(JAVAC_DIR) $(SRC) test_files/$(ERROR_TEST_FILE) $(NUMBER_RECOVERIES) || true
