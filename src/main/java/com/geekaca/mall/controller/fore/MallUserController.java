package com.geekaca.mall.controller.fore;

import com.geekaca.mall.controller.fore.param.MallUserLoginParam;
import com.geekaca.mall.service.MallUserService;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class MallUserController {
    @Autowired
    private MallUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody @Valid MallUserLoginParam userLoginParam){
        String loginToken = userService.login(userLoginParam);
        if (loginToken == null) {
            return ResultGenerator.genFailResult("登入失败");
        }else {
            return ResultGenerator.genSuccessResult();
        }
    }
}
