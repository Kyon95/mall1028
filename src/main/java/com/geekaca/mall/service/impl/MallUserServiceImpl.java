package com.geekaca.mall.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.geekaca.mall.common.MallConstants;
import com.geekaca.mall.controller.fore.param.MallUserLoginParam;
import com.geekaca.mall.domain.MallUser;
import com.geekaca.mall.exceptions.LoginFailException;
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
        //String passMd5 = SecureUtil.md5(userLoginParam.getPasswordMd5());
        MallUser checkLogin = userMapper.checkLogin(userLoginParam.getLoginName(), userLoginParam.getPasswordMd5());
        if (checkLogin == null) {
//            return null;
            throw new LoginFailException("登陆失败", MallConstants.CODE_USER_LOGIN_FAIL);
        }
        String token = JwtUtil.createToken(checkLogin.getUserId().toString(), checkLogin.getLoginName());
        return token;
    }

    @Override
    public boolean register(String loginName, String password) {
        if (userMapper.isRegistered(loginName) != null) {
            return false;
        }

        MallUser mallUser = new MallUser();
        mallUser.setLoginName(loginName);
        String passMd5 = SecureUtil.md5(password);
        mallUser.setPasswordMd5(passMd5);
        int isRegister = userMapper.insertSelective(mallUser);
        if (isRegister > 0) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public MallUser getUserInfo(String loginName) {
        return userMapper.getUserInfo(loginName);
    }

    @Override
    public MallUser editUserInfo(Long userId,String nickName,String passwordMd5,String introduceSign) {
        if (nickName == null && introduceSign == null && passwordMd5 == null ) {
            return null;
        }
        MallUser mallUser = new MallUser();
        mallUser.setUserId(userId);
        mallUser.setIntroduceSign(introduceSign);
        mallUser.setPasswordMd5(passwordMd5);
        mallUser.setNickName(nickName);
        int isEdit = userMapper.updateByPrimaryKeySelective(mallUser);
        if(isEdit > 0){
            return mallUser;
        }else {
            return null;
        }
    }

    @Override
    public boolean isLogin(Long userId) {
        MallUser mallUser = userMapper.selectByPrimaryKey(userId);
        if (mallUser == null) {
            return false;
        }else {
            return true;
        }
    }
}
