package com.geekaca.mall.config;

import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.common.Constants;
import com.geekaca.mall.exceptions.NotLoginException;
import com.geekaca.mall.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    JedisPool jedisPool;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("Token");
        if (token == null || token.equals("")) {
            throw new NotLoginException(Constants.NO_LOGIN, "未携带token");
        }
        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim idClaim = stringClaimMap.get("id");
        /**
         * 检验用户身份 把登录成功 的token 存入redis
         */
        String uid = idClaim.asString();
        try(Jedis jedis = jedisPool.getResource();){
            String redisAdminToken = jedis.get("uid:admin:" + uid);
            String redisUserToken = jedis.get("uid:user:" + uid);

            if (redisAdminToken == null && redisUserToken == null){
                // 说明token过期，或者被删除了token
                throw new NotLoginException(Constants.NO_LOGIN, "token超时");
            }
        }
        return true;
    }
}
