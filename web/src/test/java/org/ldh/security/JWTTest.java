package org.ldh.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JWTTest {

    @Test
    public void contextLoads() {
        Map<String,Object> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,90);
        // 生成token
        String token = JWT.create()
                .withHeader(map)
                .withClaim("username","张三")
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256("token!#@&(HIU"));
        System.out.println(token);
    }

    @Test
    public void examToken() {
        // 创建验证对象
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("token!#@&(HIU")).build();
        DecodedJWT verify = verifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NzE0MzUzODYsInVzZXJuYW1lIjoi5byg5LiJIn0.DjI0y82oDwI0R987pN8HZ_J7uGHus9ExHu3Rhl-x-L8");
        System.out.println(verify.getClaim("username").asString());
    }
}
