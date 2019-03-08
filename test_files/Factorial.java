class Factorial {
    int[] factorials;
    int donemax;

    boolean inited;

    public Factorial init(int size) {
        donemax = 0;
        factorials = new int[size];
        factorials[0] = 1;
        factorials[1] = 1;
        inited = true;
        return this;
    }

    public int printAll() {
        int current;

        current = 0;
        while (current < donemax + 1) {
            io.println(current);
            io.println(factorials[current]);
            current = current + 1;
        }

        return current;
    }

    public int compute(int num) {
        int value;

        if (num < 2)
            value = 1;
        else if (num < donemax + 1) {
            value = factorials[num];
        }
        else
            value = num * (this.compute(num - 1));

        return value;
    }

    public static void main(String[] args) {
        Factorial calculator;
        int max;

        max = Integer.parseInt(args[0]);
        calculator = new Factorial().init(max);

        while (0 < max) {
            calculator.compute(max);
            max = max - 1;
        }

        calculator.printAll();
    }
}
