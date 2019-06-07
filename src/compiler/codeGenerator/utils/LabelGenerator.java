package compiler.codeGenerator.utils;

public class LabelGenerator {

    private static int counter = 1;

    public static String nextLabel() {
        return "Label_" + counter++;
    }
}
