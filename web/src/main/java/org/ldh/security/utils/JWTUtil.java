package org.ldh.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUtil {

    private static final String SIGNAL = "U*(@(F_+$H@!^%#>US";

    /**
     * 生成token  header.payload.sign
     * @return
     */
    public static String getToken(Map<String,String> map) {
        Calendar calendar = Calendar.getInstance();
        // 过期时间
        calendar.add(Calendar.MILLISECOND,1000*60*60*24);
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        // 设置过期时间
        builder.withExpiresAt(calendar.getTime());
        // 签名
        String token = builder.sign(Algorithm.HMAC256(SIGNAL));
        return token;
    }

    /**
     * 验证token
     * @param token
     */
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(SIGNAL)).build().verify(token);
    }

//    public static DecodedJWT getTokenInfo(String token) {
//        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGNAL)).build().verify(token);
//        return verify;
//    }
}
