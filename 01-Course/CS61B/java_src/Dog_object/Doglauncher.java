public class Doglauncher {
    public static void main (String[] args) {

    Dog d1 = new Dog(20);
    Dog d2 = new Dog(100);

    d1.makenoise();
    d2.makenoise();

    Dog heavier = Dog.maxdog(d1, d2);
    System.out.println("Heavier dog's weight is " + heavier.weightInPound);

    Dog heavier1 = d1.maxdog(d2);
    System.out.println("Heavier dog's weight is " + heavier1.weightInPound);

    System.out.println(Dog.binomen);
    }
}
