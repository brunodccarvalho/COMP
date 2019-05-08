package compiler.codeGenerator;

public abstract class BaseByteCode {

    protected String regexReplace;

    public BaseByteCode()
    {
    }

    protected static String subst(String regex, String... substitutes) {
        for(String substitute: substitutes) {
            regex = regex.replaceFirst("\\?", substitute);
        }
        return regex;
    }

    @Override
    public abstract String toString();
    
}