package com.geekaca.mall.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.geekaca.mall.common.Constants;
import com.geekaca.mall.exceptions.NotLoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: Jwt工具类，生成JWT和认证
 */
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    /**
     * 密钥
     * 密码本
     * 不要泄露出去！！！！！
     */
    private static final String SECRET = "Geekaca3589$%$%#$##$%(*^(";

    /**
     * 过期时间
     **/
    private static final long EXPIRATION = 60 * 60 * 24 * 7L;//单位为秒 7天

    public static void main(String[] args) {
//        User user = new User("tom", "abc");
//        user.setId(999);
//        String token = createToken(user);
//        System.out.println(token);
//
//        Map<String, Claim> stringClaimMap = verifyToken(token);
//        Claim id = stringClaimMap.get("id");
//        System.out.println("id: " + id.asInt());
//        System.out.println("token中的userName:" + stringClaimMap.get("userName").asString());
    }

    /**
     * 生成用户token,设置token超时时间
     */
    public static String createToken(String uid, String uname) {
        //token 过期时间   1天后过期
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(map)// 添加头部
                //=====向token中加入自定义的信息
                // 可以将基本信息放到claims中
                .withClaim("id", uid) //userId
                .withClaim("userName", uname)//userName
//                .withClaim("userType",user.getUserType())

                .withExpiresAt(expireDate) //超时设置,设置过期的日期
                .withIssuedAt(new Date()) //签发时间
                .sign(Algorithm.HMAC256(SECRET)); //SECRET加密
        return token;
    }

    /**
     * 校验token并解析token
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);

            //decodedJWT.getClaim("属性").asString()  获取负载中的属性值

        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("token解码异常");
            throw new NotLoginException(Constants.NO_LOGIN, "Token解析异常");
            //解码异常则抛出异常
        }
        return jwt.getClaims();
    }

}