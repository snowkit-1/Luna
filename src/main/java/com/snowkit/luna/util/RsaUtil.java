package com.snowkit.luna.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RsaUtil {

    public static byte[] encrypt(String plainData, PublicKey publicKey) {
        return encrypt(plainData.getBytes(StandardCharsets.UTF_8), publicKey);
    }

    public static byte[] encrypt(byte[] plainData, PublicKey publicKey) {
        byte[] encryptedData;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            encryptedData = cipher.doFinal(plainData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to encrypt.");
        }

        return encryptedData;
    }

    public static PublicKey getPublicKey(String publicKeyBase64) {
        byte[] x509PublicKey = Base64.getDecoder().decode(publicKeyBase64);

        PublicKey publicKey;
        try {
            publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(x509PublicKey));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get public key.");
        }

        return publicKey;
    }

    /**
     * Generate PublicKey, PrivateKey (RSA 2048 bit)
     */
    public static KeyPair generateKeyPair() {
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Generate key pair failed.");
        }
        keyPairGenerator.initialize(2048, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.genKeyPair();

        return keyPair;
    }

    public static byte[] decrypt(byte[] encryptedData, PrivateKey privateKey) {
        byte[] plainData;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            plainData = cipher.doFinal(encryptedData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to decrypt.");
        }

        return plainData;
    }

    public static PrivateKey getPrivateKey(String privateKeyBase64) {
        byte[] pkcs8PrivateKey = Base64.getDecoder().decode(privateKeyBase64);

        PrivateKey privateKey;
        try {
            privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(pkcs8PrivateKey));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get private key.");
        }

        return privateKey;
    }
}
