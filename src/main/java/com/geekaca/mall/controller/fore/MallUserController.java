package com.geekaca.mall.controller.fore;

import com.geekaca.mall.controller.fore.param.MallUserLoginParam;
import com.geekaca.mall.controller.fore.param.MallUserRegisterParam;
import com.geekaca.mall.service.MallUserService;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * todo：
 * 登入
 * 注册
 */
@RestController
@RequestMapping("/api/v1")
public class MallUserController {
    @Autowired
    private MallUserService userService;

    @PostMapping("/user/login")
    public Result login(@RequestBody MallUserLoginParam userLoginParam){
        String loginToken = userService.login(userLoginParam);
        if (loginToken == null) {
            return ResultGenerator.genFailResult("登入失败");
        }else {
            Result result = ResultGenerator.genSuccessResult();
            result.setData(loginToken);
            return result;
        }
    }

    @PostMapping("/user/register")
    @ApiOperation(value = "用户注册")
    public Result register(@RequestBody @Valid MallUserRegisterParam userRegisterParam){
        boolean isRegister = userService.register(userRegisterParam.getLoginName(), userRegisterParam.getPassword());
        if (isRegister == false) {
            return ResultGenerator.genFailResult("注册失败");
        }else {
            return ResultGenerator.genSuccessResult();
        }
    }


}
