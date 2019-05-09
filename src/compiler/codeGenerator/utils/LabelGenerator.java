package compiler.codeGenerator.utils;

public class LabelGenerator {

    private int counter;

    public LabelGenerator() {
        this.counter = 1;
    }

    public String nextLabel() {
        return "Label" + this.counter++;
    }
}