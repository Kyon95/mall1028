package com.geekaca.mall.controller.fore;

import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.controller.fore.param.MallUserLoginParam;
import com.geekaca.mall.controller.fore.param.MallUserRegisterParam;
import com.geekaca.mall.domain.MallUser;
import com.geekaca.mall.service.MallUserService;
import com.geekaca.mall.utils.JwtUtil;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;


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

    @GetMapping("/user/info")
    @ApiOperation(value = "展示用户信息")
    private Result getUserInfo(@RequestHeader("Token")String token){
        Map<String, Claim> userToken = JwtUtil.verifyToken(token);
        Claim userNameClaim = userToken.get("userName");
        String userName = userNameClaim.asString();

        MallUser userInfo = userService.getUserInfo(userName);
        if (userInfo == null) {
            return ResultGenerator.genFailResult("展示用户信息失败");
        }else {
            Result result = ResultGenerator.genSuccessResult();
            result.setData(userInfo);
            return result;
        }

    }


}
