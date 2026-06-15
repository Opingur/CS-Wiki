public class Dog {
    public int weightInPound;

    public static String binomen = "Canis familiaris";
    public void makenoise () {
        if (weightInPound < 10) {
            System.out.println("pip pip pip");
        } else if (weightInPound < 20) {
            System.out.println("bark bark bark");
        } else {
            System.out.println("Woof!");
        }
    }
    public Dog (int w) {
        weightInPound = w;
    }
    public static Dog maxdog (Dog d1, Dog d2) {
        if (d1.weightInPound > d2.weightInPound) {
            return d1;
        }
        return d2;
    }
    public Dog maxdog (Dog d2) {
        if (this.weightInPound > d2.weightInPound) {
            return this;
        }
        return d2;
    }
}