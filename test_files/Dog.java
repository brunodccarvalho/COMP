class Dog {

    int availableFood;
    int hoursSleep;
    int numberKm;
    public static void main(String[] args) {
        Dog dog;
        dog.sleep();
    }

    public int run() {
        boolean full;
        int sleepEatFactor;
        this.sleep();
        sleepEatFactor = hoursSleep / (availableFood + 5 * 2);
        if(full) {
            sleepEatFactor = hoursSleep / (availableFood + 5 * 2);
        } else {
            numberKm = numberKm + 1;
        }
        return numberKm;
    }
}