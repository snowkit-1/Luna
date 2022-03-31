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

    public static Envelope encrypt(String plainData, byte[] aesKey) {
        Cipher cipher = getCipher();
        SecretKey secretKey = getSecretKey(aesKey);

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
                .encryptedData(encryptedDataBase64)
                .iv(ivBase64)
                .build();

        return envelope;
    }

    public static String decrypt(String encryptedDataBase64, byte[] aesKey, String ivBase64) {
        byte[] encryptedData = Base64.getDecoder().decode(encryptedDataBase64);
        byte[] iv = Base64.getDecoder().decode(ivBase64);

        return decrypt(encryptedData, aesKey, iv);
    }

    public static String decrypt(byte[] encryptedData, byte[] aesKey, byte[] iv) {
        Cipher cipher = getCipher();
        SecretKey secretKey = getSecretKey(aesKey);

        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            throw new RuntimeException("Cipher init failed.");
        }

        String plainData = getPlainData(cipher, encryptedData);

        return plainData;
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

    private static SecretKey getSecretKey(byte[] aesKey) {
        return new SecretKeySpec(aesKey, "AES");
    }

    private static byte[] getEncryptedData(Cipher cipher, String plainData) {
        byte[] plainDataBytes = plainData.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedData;
        try {
            encryptedData = cipher.doFinal(plainDataBytes);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to encrypt.");
        }

        return encryptedData;
    }

    private static String getPlainData(Cipher cipher, byte[] encryptedData) {
        byte[] plainDataBytes;
        try {
            plainDataBytes = cipher.doFinal(encryptedData);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to decrypt.");
        }

        String plainData = new String(plainDataBytes);

        return plainData;
    }
}
