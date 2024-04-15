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
import java.util.Arrays;
import java.util.List;
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
        //放行mall商场首页，不登入也能浏览首页
        String requestUri = request.getRequestURI();
        if (isExcludedPath(requestUri)) {
            return true;
        }

        // 对后台管理接口进行拦截
        if (requestUri.startsWith("/manage-api/v1")) {
            return handleToken(request, "uid:admin:");
        }

        // 对前台管理接口进行拦截
        if (requestUri.startsWith("/api/v1")) {
            return handleToken(request, "uid:user:");
        }

        return true;
    }

    // 判断请求路径是否在排除列表中
    private boolean isExcludedPath(String requestUri) {
        // 定义不需要拦截的路径列表
        List<String> excludedPaths = Arrays.asList("/#/home");
        // 检查请求的路径是否在排除列表中
        return excludedPaths.stream().anyMatch(requestUri::endsWith);
    }


    private boolean handleToken(HttpServletRequest request, String redisKeyPrefix) {
        // 实现 Token 的验证逻辑
        String token = request.getHeader("Token");
        if (token == null || token.equals("")) {
            throw new NotLoginException(Constants.NO_LOGIN, "未携带token");
        }
        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim idClaim = stringClaimMap.get("id");
        String uid = idClaim.asString();
        try(Jedis jedis = jedisPool.getResource();){
            String redisToken = jedis.get(redisKeyPrefix + uid);
            if (redisToken == null){
                // 说明token过期，或者被删除了token
                throw new NotLoginException(Constants.NO_LOGIN, "token无效或超时");
            }
        }
        return true;
    }

}
