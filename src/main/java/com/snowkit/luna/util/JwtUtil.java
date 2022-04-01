package com.snowkit.luna.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private final static long HOUR = 60 * 60 * 1000;

    private final static long DAY = 24 * HOUR;

    private final static long PERIOD = DAY;

    private final static Algorithm ALGORITHM = Algorithm.HMAC256(KeyUtil.JWT_SECRET_KEY);

    public static String create(Map<String, Object> claims) {
        Date now = new Date();
        String token = JWT.create()
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + PERIOD))
                .withPayload(claims)
                .sign(ALGORITHM);

        return token;
    }

    public static void validate(String token) {
        JWTVerifier verifier = JWT.require(ALGORITHM).build();

        verifier.verify(token);
    }

    public static String getHeader(String token) {
        return getOriginal(JWT.decode(token).getHeader());
    }

    public static String getPayload(String token) {
        return getOriginal(JWT.decode(token).getPayload());
    }

    public static String createKey() {
        byte[] key = Aes256Util.createKey();

        return Base64.getEncoder().encodeToString(key);
    }

    private static String getOriginal(String base64Encoded) {
        byte[] original = Base64.getDecoder().decode(base64Encoded);

        return new String(original);
    }
}
