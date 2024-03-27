package com.geekaca.mall.service;

import com.geekaca.mall.controller.admin.param.AdminLoginParam;
import com.geekaca.mall.domain.AdminUser;

public interface AdminUserService {
    /**
     * 校验登录
     * @param adminLoginParam
     * @return token
     */
    String login(AdminLoginParam adminLoginParam);

    AdminUser selectAdminById(Long id);
}
