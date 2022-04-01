package com.snowkit.luna.util;

import com.snowkit.luna.model.Envelope;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AES256Util {

    /**
     * Generate secret key (save in environment variable)
     */
    public static byte[] createKey() {
        KeyGenerator keyGen;
        try {
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get instance.");
        }
        keyGen.init(256);// 128, 192, 256
        SecretKey secretKey = keyGen.generateKey();
        byte[] aesKey = secretKey.getEncoded();

        return aesKey;
    }

    public static Envelope encrypt(String plainData, SecretKey secretKey) {
        return encrypt(plainData.getBytes(StandardCharsets.UTF_8), secretKey);
    }

    public static Envelope encrypt(byte[] plainData, SecretKey secretKey) {
        Cipher cipher = getCipher();

        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new RuntimeException("Cipher init failed.");
        }

        byte[] encryptedData = getEncryptedData(cipher, plainData);
        String encryptedDataBase64 = Base64.getEncoder().encodeToString(encryptedData);
        byte[] iv = cipher.getIV();
        String ivBase64 = Base64.getEncoder().encodeToString(iv);

        Envelope envelope = Envelope.builder()
                .encData(encryptedDataBase64)
                .iv(ivBase64)
                .build();

        return envelope;
    }

    public static byte[] decrypt(String encryptedDataBase64, SecretKey secretKey, String ivBase64) {
        byte[] encryptedData = Base64.getDecoder().decode(encryptedDataBase64);
        byte[] iv = Base64.getDecoder().decode(ivBase64);

        return decrypt(encryptedData, secretKey, iv);
    }

    public static byte[] decrypt(byte[] encryptedData, SecretKey secretKey, byte[] iv) {
        Cipher cipher = getCipher();

        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            throw new RuntimeException("Cipher init failed.");
        }

        byte[] plainData = getPlainData(cipher, encryptedData);

        return plainData;
    }

    public static SecretKey getSecretKey(String aesKeyBase64) {
        byte[] aesKey = Base64.getDecoder().decode(aesKeyBase64);

        return getSecretKey(aesKey);
    }

    public static SecretKey getSecretKey(byte[] aesKey) {
        return new SecretKeySpec(aesKey, "AES");
    }

    private static Cipher getCipher() {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get Cipher.");
        }

        return cipher;
    }

    private static byte[] getEncryptedData(Cipher cipher, byte[] plainData) {
        byte[] encryptedData;
        try {
            encryptedData = cipher.doFinal(plainData);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to encrypt.");
        }

        return encryptedData;
    }

    private static byte[] getPlainData(Cipher cipher, byte[] encryptedData) {
        byte[] plainData;
        try {
            plainData = cipher.doFinal(encryptedData);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to decrypt.");
        }

        return plainData;
    }
}
