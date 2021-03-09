package com.vasnatech.commons.random;

import java.util.Random;

public class Randoms {

    static Random RANDOM = new Random();

    public static String hex() {
        return hex(16);
    }

    public static String hex(int size) {
        int normalizedSize = Math.min(Math.max(0, size), 128);
        byte[] randomBytes = new byte[normalizedSize];
        RANDOM.nextBytes(randomBytes);
        return toHexString(randomBytes);
    }

    static int HIGH_DIGIT_MASK = 0xF0;
    static int LOW_DIGIT_MASK = 0x0F;
    static char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    static String toHexString(byte[] data) {
        final int length = data.length;
        final char[] hexChars = new char[length << 1];
        for (int index = 0, j = 0; index < length; index++) {
            hexChars[j++] = HEX_DIGITS[(HIGH_DIGIT_MASK & data[index]) >>> 4];
            hexChars[j++] = HEX_DIGITS[LOW_DIGIT_MASK & data[index]];
        }
        return new String(hexChars);
    }
}
