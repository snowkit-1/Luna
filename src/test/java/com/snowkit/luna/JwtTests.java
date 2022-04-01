package com.snowkit.luna;

import com.snowkit.luna.util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

public class JwtTests {

    public static void main(String[] args) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("data", "hello");
        String jwt = JwtUtil.create(claims);
        System.out.println(jwt);

        System.out.println(JwtUtil.getHeader(jwt));
        System.out.println(JwtUtil.getPayload(jwt));

        String token = jwt;
        JwtUtil.validate(token);
    }
}
