import java.util.Random;

public class RandomInteger {
    public static int getRandomInteger() {
        Random random = new Random();

        return (random.nextInt(64) + 1);
    }
}
