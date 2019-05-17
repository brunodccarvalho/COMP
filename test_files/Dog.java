class Dog {
  int availableFood;
  int hoursSleep;
  int numberKm;
  int[] foodEaten;

  public static void main(String[] args) {
    boolean full;
    Dog dog;
    ioPlus.printResult(100*100);
    full = false;
    dog = new Dog();
    dog.run(10,10);
  }
  public int run(int a, int b) {
    int[] array;
    int i;
    array = new int[11];
    i = 0;

    while(i < 10) {
      ioPlus.printResult(i);
      array[i] = i;
      i = i + 1;
    }

    i = 0;

    while(i < 11) {
      ioPlus.printResult(array[i]);
      i = i + 1;
    }
  
    return i;
  }
}
