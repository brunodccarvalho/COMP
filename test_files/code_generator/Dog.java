class Dog {

    int availableFood;
    int hoursSleep;
    int numberKm;
    public static void main(String[] args) {
        Dog dog;
        dog.eat();
    }

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
        this.sleep();
        sleepEatFactor = hoursSleep / (availableFood + 5 * 2);
        return numberKm;
    }
}