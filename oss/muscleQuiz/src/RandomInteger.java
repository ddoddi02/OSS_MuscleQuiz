import java.security.SecureRandom;

public class RandomInteger {
    public static int getRandomInteger() {
        SecureRandom random = new SecureRandom(); // 난수 생성기
        
        return (random.nextInt(32) + 1);
    }
}
