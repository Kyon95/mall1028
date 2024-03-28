package com.geekaca.mall.service;


import com.geekaca.mall.controller.fore.param.MallUserLoginParam;
import com.geekaca.mall.domain.MallUser;

/**
 * todo：
 * 1.前台登入
 */
public interface MallUserService {
    String login(MallUserLoginParam userLoginParam);

    boolean register(String username, String password);

    MallUser getUserInfo(String loginName);

    MallUser editUserInfo(Long userId,String nickMame,String passwordMd5,String introduceSign);
}
