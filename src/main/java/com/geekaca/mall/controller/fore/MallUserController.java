package com.geekaca.mall.controller.fore;

import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.controller.fore.param.MallUserLoginParam;
import com.geekaca.mall.controller.fore.param.MallUserRegisterParam;
import com.geekaca.mall.controller.fore.param.MallUserUpdateParam;
import com.geekaca.mall.domain.MallUser;
import com.geekaca.mall.service.MallUserService;
import com.geekaca.mall.utils.JwtUtil;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @RequestMapping("/user/info")
    @ApiOperation(value = "修改用户信息")
    private Result editUserInfo(@RequestHeader("Token")String token, @RequestBody MallUserUpdateParam mallUser){
        Map<String, Claim> userToken = JwtUtil.verifyToken(token);
        Claim id = userToken.get("id");
        String userId = id.asString();
        String introduceSign = mallUser.getIntroduceSign();
        String nickName = mallUser.getNickName();
        String passwordMd5 = mallUser.getPasswordMd5();
        MallUser editUserInfo = userService.editUserInfo(Long.parseLong(userId), nickName, passwordMd5,introduceSign);
        System.out.println(editUserInfo);
        if (editUserInfo == null) {
            return ResultGenerator.genFailResult("编辑用户信息失败");
        }else {
            Result result = ResultGenerator.genSuccessResult();
            result.setData(editUserInfo);
            return result;
        }
    }

    @PostMapping("/user/logout")
    private Result logout(@RequestHeader("Token")String token,HttpServletRequest req){
        Map<String, Claim> userToken = JwtUtil.verifyToken(token);
        if (userToken == null) {
            return ResultGenerator.genFailResult("用户未登入，登出失败");
        }
        Claim id = userToken.get("id");
        long userId = Long.parseLong(id.asString());
        boolean isLogin = userService.isLogin(userId);
        if (isLogin == false) {
            return ResultGenerator.genFailResult("登出失败");
        }else {
            HttpSession session = req.getSession();
            session.removeAttribute("Token");
            return ResultGenerator.genSuccessResult();
        }
    }



}
