package com.geekaca.mall.service.impl;

import com.geekaca.mall.config.JedisConfig;
import com.geekaca.mall.controller.admin.param.AdminLoginParam;
import com.geekaca.mall.domain.AdminUser;
import com.geekaca.mall.mapper.AdminUserMapper;
import com.geekaca.mall.service.AdminUserService;
import com.geekaca.mall.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    JedisPool jedisPool;
    @Autowired
    private AdminUserMapper adminUserMapper;


    @Override
    public String login(AdminLoginParam adminLoginParam) {
        // 需要把用户传递的密码md5加密
//        String passMd5 = SecureUtil.md5(adminLoginParam.getPasswordMd5());

        AdminUser adminUser = adminUserMapper.checkLogin(adminLoginParam.getUserName(), adminLoginParam.getPasswordMd5());
        if(adminUser==null){
            //登录失败
            return null;
        }
        String token = JwtUtil.createToken(adminUser.getAdminUserId().toString(), adminUser.getLoginUserName());
        // 把用户信息缓存到token
        try( Jedis jedis = jedisPool.getResource();){
            String key = "uid:admin:" + adminUser.getAdminUserId();
            jedis.set(key,token);
            jedis.expire(key,60*60*3);
        }
        return token;
    }

    @Override
    public AdminUser selectAdminById(Long id) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(id);
        return adminUser;
    }
}
