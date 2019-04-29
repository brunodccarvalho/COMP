class Dog {

    int availableFood;
    int hoursSleep;
    int numberKm;

    public int sleep() {
        hoursSleep = hoursSleep + 1;
        return hoursSleep;
    }

    public boolean eat(int quantity, int mult) {
        availableFood = availableFood - mult * quantity;
        return (availableFood < 1); 
    }

    public int run() {
        boolean full;
        int sleepEatFactor;
        numberKm = numberKm + 1;
        full = this.eat(3);
        this.sleep();
        sleepEatFactor = hoursSleep / (availableFood + 5 * 2);
        return numberKm;
    }
}