SRC = Program

jjtree:
	jjtree jjt/$(SRC).jjt 
	javacc $(SRC).jj
	javac $(SRC).java

compile:
	javacc $(SRC).jj
