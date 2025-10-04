package com.vasnatech.commons.random;

import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base16;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.BinaryCodec;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Randoms {

    static final Random RANDOM = new SecureRandom();

    static byte[] randomBytes(int size) {
        int normalizedSize = Math.min(Math.max(0, size), 128);
        byte[] randomBytes = new byte[normalizedSize];
        RANDOM.nextBytes(randomBytes);
        return randomBytes;
    }

    public static String hex() {
        return hex(16);
    }

    public static String hex(int size) {
        return base16(size);
    }

    static final Map<Integer, Class<? extends BinaryEncoder>> ENCODER_CLASSES = Map.of(
            2, BinaryCodec.class,
            16, Base16.class,
            32, Base32.class,
            64, Base64.class
    );

    static final Map<Integer, BinaryEncoder> ENCODERS = new HashMap<>();

    static BinaryEncoder encoder(int base) {
        if (ENCODERS.containsKey(base)) {
            return ENCODERS.get(base);
        }
        Class<? extends BinaryEncoder> binaryEncoderClass = ENCODER_CLASSES.get(base);
        if (binaryEncoderClass == null) {
            throw new IllegalArgumentException("Unsupported base: " + base);
        }
        BinaryEncoder binaryEncoder = null;
        try {
            binaryEncoder = binaryEncoderClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException  | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new UnsupportedOperationException("Unable to instantiate " + binaryEncoderClass.getName(), e);
        }
        ENCODERS.put(base, binaryEncoder);
        return binaryEncoder;
    }

    public static String base2(int size) {
        return randomString(2, size);
    }

    public static String base16(int size) {
        return randomString(16, size);
    }

    public static String base32(int size) {
        return randomString(32, size);
    }

    public static String base64(int size) {
        return randomString(64, size);
    }

    private static String randomString(int base, int size) {
        try {
            return new String(encoder(base).encode(randomBytes(size)), StandardCharsets.UTF_8);
        } catch (EncoderException e) {
            throw new UnsupportedOperationException("Unable to encode base " + base, e);
        }
    }
}
