package code_base.util.model;

import java.util.Random;

/**
 * Created by net on 2018/4/27.
 * 随机数据工具类
 */

public class RandomUtils {

    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * 生成16个随机字符串
     */
    public static String generate16RandomString(){
        return getRandomString(16);
    }

    /**
     * @param count 要生成的字符数量，即要生成多长的随机字符串
     */
    public static String getRandomString(int count){
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            int randomInt = random.nextInt(ALPHABET.length());
            char randomChar = ALPHABET.charAt(randomInt);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }
}
