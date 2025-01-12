import java.security.SecureRandom;

public class RandomInteger {
    public static int getRandomInteger() {
        SecureRandom random = new SecureRandom();
        
        return (random.nextInt(32) + 1);
    }
}
