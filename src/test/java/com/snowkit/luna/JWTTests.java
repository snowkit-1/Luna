package com.snowkit.luna;

import com.snowkit.luna.util.JWTUtil;

import java.util.HashMap;
import java.util.Map;

public class JWTTests {

    public static void main(String[] args) {
        Map<String, String> claims = new HashMap<>();
        claims.put("data", "hello");
        String jwt = JWTUtil.create(claims);
        System.out.println(jwt);

        System.out.println(JWTUtil.getHeader(jwt));
        System.out.println(JWTUtil.getPayload(jwt));

        String token = jwt;
        JWTUtil.validate(token, claims);
    }
}
