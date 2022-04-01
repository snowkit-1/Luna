package com.snowkit.luna.util;

import lombok.Getter;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtil {

    public static String sha256(byte[] plainData) {
        return hash(plainData, Algorithm.SHA_256);
    }

    public static String sha512(byte[] plainData) {
        return hash(plainData, Algorithm.SHA_512);
    }

    private static String hash(byte[] plainData, Algorithm algorithm) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(algorithm.getValue());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to hash.");
        }

        byte[] hashedData = messageDigest.digest(plainData);

        return bytesToHex(hashedData);
    }

    private static String bytesToHex(byte[] hashedData) {
        StringBuilder hexString = new StringBuilder(2 * hashedData.length);
        for (int i = 0; i < hashedData.length; i++) {
            String hex = Integer.toHexString(0xff & hashedData[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    private enum Algorithm {

        SHA_256("SHA-256"),
        SHA_512("SHA-512");

        @Getter
        private final String value;

        Algorithm(String value) {
            this.value = value;
        }
    }
}