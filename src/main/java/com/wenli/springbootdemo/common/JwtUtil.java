package com.wenli.springbootdemo.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtUtil {


    private static final long expire_time = 24*60*60 * 1000;

    // 校验方法
    public static boolean verify(String token, String username, String secret) {

        try {
            //校验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            //吧校验器加载到验证器
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username)
                    .build();


            DecodedJWT verify = verifier.verify(token);
            return true;
        } catch (Exception e) {
            log.debug("ex happen");
            return false;
        }


    }

    //签名方法
    public static String sign(String username,String secret){
        //过期时间
        Date date = new Date(System.currentTimeMillis()+expire_time);

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create().withClaim("username",username)
                .withExpiresAt(date).sign(algorithm);



    }


    public static String  getUsername(String token){

        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        }catch (Exception e){
            return null;
        }



    }


}
