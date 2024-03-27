package com.geekaca.mall.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.geekaca.mall.controller.fore.param.MallUserLoginParam;
import com.geekaca.mall.domain.MallUser;
import com.geekaca.mall.mapper.MallUserMapper;
import com.geekaca.mall.service.MallUserService;
import com.geekaca.mall.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MallUserServiceImpl implements MallUserService {
    @Autowired
    private MallUserMapper userMapper;


    @Override
    public String login(MallUserLoginParam userLoginParam) {
        String passMd5 = SecureUtil.md5(userLoginParam.getPasswordMd5());
        MallUser checkLogin = userMapper.checkLogin(userLoginParam.getUsername(), passMd5);
        if (checkLogin == null) {
            return null;
        }
        String token = JwtUtil.createToken(checkLogin.getUserId().toString(), checkLogin.getLoginName());
        return token;
    }
}
