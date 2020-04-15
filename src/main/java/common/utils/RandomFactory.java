package common.utils;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RandomFactory {

    /**
     *
     * @return random string sequence
     */
    public static String String() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     *
     * @return true or false
     */
    public static boolean Boolean() {
        Random random = new Random();
        return random.nextBoolean();
    }

    /**
     * @param max: the max value to return
     * @return a positive integer between 0 and the supplied max
     */
    public static int Int(int max) {
        Random random = new Random();
        return Math.abs(random.nextInt(max));
    }

    /**
     * @param length: the length of bytes to return
     * @return an array of random bytes
     */
    public static byte[] Bytes(int length) {
        Random random = new Random();
        byte[] b = new byte[length];
        random.nextBytes(b);
        return b;
    }

    /**
     * @return a random HEX color
     */
    public static String Color() {
        Random random = new Random();
        int rand_num = random.nextInt(0xffffff + 1);
        return String.format("#%06x", rand_num);
    }

    public static Instant Instant() {
        return Instant.ofEpochSecond(Math.abs(ThreadLocalRandom.current().nextInt()));
    }
}
