package com.ussp;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGen() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", "1");
        claims.put("username", "billy");
        // 生成的JWT令牌
        String token = JWT.create()
                .withClaim("user", claims)  // 添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000 * 24))  // 设置过期时间
                .sign(Algorithm.HMAC256("secret")); // 设置签名
        System.out.println(token);
    }

    @Test
    public void testParse() {
        // 定义字符串，模拟用户传过来的token
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7ImlkIjoiMSIsInVzZXJuYW1lIjoiYmlsbHkifSwiZXhwIjoxNzU5Mzg5MjY4fQ" +
                ".s_jgWRwNo5rXxebeR7RCXGBnzwZEUTiX4pv8uVQnhys";
        DecodedJWT decodeJWT = JWT.require(Algorithm.HMAC256("secret")).build().verify(token);
        Map<String, Claim> claims = decodeJWT.getClaims();
        System.out.println(claims.get("user"));

        // 如果篡改了头部和载荷部分的数据，那么验证失败
        // 如果密钥改了，验证失败
        // 如果token过期了，验证失败
    }
}
