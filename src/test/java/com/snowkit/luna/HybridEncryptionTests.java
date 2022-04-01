package com.snowkit.luna;

import com.snowkit.luna.model.Envelope;
import com.snowkit.luna.util.AES256Util;
import com.snowkit.luna.util.KeyUtil;
import com.snowkit.luna.util.RSAUtil;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class HybridEncryptionTests {

    public static void main(String[] args) {
        // 서버에서 암호화 (서버: AES Key는 있지만, RSA Key Pair는 없음)
        String publicKeyInRequest = KeyUtil.PUBLIC_KEY;
        Envelope envelope = encrypt("Hello", publicKeyInRequest);
        System.out.println("encryptedData: " + envelope.getEncData());

        // 클라이언트에서 복호화 (클라이언트: RSA Key Pair는 있지만, AES Key는 없음)
        byte[] plainData = decrypt(envelope);
        String plainText = new String(plainData);
        System.out.println("plainText: " + plainText);
    }

    public static Envelope encrypt(String plainData, String publicKeyInRequest) {
        // 1. 원본 데이터를 AES 암호화
        SecretKey secretKey = AES256Util.getSecretKey(KeyUtil.AES_KEY);
        Envelope envelope = AES256Util.encrypt(plainData, secretKey);

        // 2. AES KEY를 RSA 암호화
        PublicKey publicKey = RSAUtil.getPublicKey(publicKeyInRequest);
        byte[] rsaEncrypted = RSAUtil.encrypt(KeyUtil.AES_KEY, publicKey);
        String rsaEncryptedBase64 = Base64.getEncoder().encodeToString(rsaEncrypted);
        envelope.setEncKey(rsaEncryptedBase64);

        return envelope;
    }

    public static byte[] decrypt(Envelope response) {
        // 1. AES KEY를 RSA 복호화
        byte[] rsaEncrypted = Base64.getDecoder().decode(response.getEncKey());
        PrivateKey privateKey = RSAUtil.getPrivateKey(KeyUtil.PRIVATE_KEY);
        byte[] aesKeyBase64Bytes = RSAUtil.decrypt(rsaEncrypted, privateKey);
        String aesKeyBase64 = new String(aesKeyBase64Bytes);
        SecretKey secretKey = AES256Util.getSecretKey(aesKeyBase64);

        // 2. AES 복호화
        byte[] plainData = AES256Util.decrypt(response.getEncData(), secretKey, response.getIv());

        return plainData;
    }
}
