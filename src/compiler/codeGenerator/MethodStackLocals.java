package compiler.codeGenerator;


/**
 * MethodStackLocals
 */
public class MethodStackLocals extends JVMInst {
    
    /**
     * 1: Locals array size
     */
    public static String LOCALS = "\t.limit locals ?";
    /**
     * 1: Stack size
     */
    public static String STACK = "\t.limit stack ?";

    private int localsSize;

    MethodStackLocals(Method belongs) {
        /**
         * TODO Calcular o numero de local size
         * 
         * in belongs:
         * protected int numberLocals; 
         * protected int numberTemp; 
         * protected int numberParam;
         * protected HashMap<Descriptor, Integer> variablesIndexes;
         */
        this.localsSize = 99;

    }

    @Override
    public String toString() {
        String methodStack = subst(MethodStackLocals.STACK, String.valueOf(localsSize));
        String methodLocals = subst(MethodStackLocals.LOCALS, String.valueOf(localsSize));
        String methodStackLocals = new String();
        return methodStackLocals.concat(methodStack).concat("\n").concat(methodLocals);
    }

}