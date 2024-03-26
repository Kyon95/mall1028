package com.geekaca.mall.controller.admin;

import com.geekaca.mall.controller.admin.param.AdminLoginParam;
import com.geekaca.mall.service.AdminUserService;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 后台用户管理接口
 */
@RestController
@RequestMapping("/manage-api/v1")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @PostMapping("/adminUser/login")
    public Result login(@Valid @RequestBody AdminLoginParam adminLoginParam) {
        String loginToken = adminUserService.login(adminLoginParam);
        if (loginToken == null) {
            return ResultGenerator.genFailResult("登录失败");
        } else {
            Result result = ResultGenerator.genSuccessResult();
            result.setData(loginToken);
            return result;
        }
    }

}
