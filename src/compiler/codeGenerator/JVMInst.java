package compiler.codeGenerator;

public abstract class JVMInst {

    public JVMInst() {}

    /**
     * Substitutes the '?' characters in a string by the provided words.
     * @param incompleteString The string which holds '?' characters to be replaced.
     * @param substitutes The words to replace the '?' characters, in the same order as they are provided
     * @return The complete string (with '?' characters replaced by the provided words)
     */
    protected static String subst(String incompleteInst, String... substitutes) {
        for(String substitute: substitutes) {
            incompleteInst = incompleteInst.replaceFirst("\\?", substitute);
        }
        return incompleteInst;
    }
}