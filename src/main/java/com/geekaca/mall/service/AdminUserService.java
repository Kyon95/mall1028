package com.geekaca.mall.service;

import com.geekaca.mall.controller.admin.param.AdminLoginParam;

public interface AdminUserService {
    /**
     * 校验登录
     * @param adminLoginParam
     * @return token
     */
    String login(AdminLoginParam adminLoginParam);
}
