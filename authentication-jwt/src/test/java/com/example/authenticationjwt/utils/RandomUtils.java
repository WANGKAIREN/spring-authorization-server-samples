package com.example.authenticationjwt.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author WANGKairen
 * @since 2023-01-20 22:07:25
 **/
public class RandomUtils {

    public static String randomText() {
        return RandomStringUtils.random(32, true, true);
    }


    private RandomUtils() {
    }

    //全数字的随机值
    public static String randomNumber() {
        return RandomStringUtils.random(32, false, true);
    }
}
