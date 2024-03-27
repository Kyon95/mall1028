package com.geekaca.mall.controller.admin;

import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.controller.admin.param.AdminLoginParam;
import com.geekaca.mall.domain.AdminUser;
import com.geekaca.mall.service.AdminUserService;
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

    @GetMapping("/adminUser/profile")
    @ApiOperation(value = "获取管理员信息",notes = "获取管理员信息显示在前端界面")
    public Result profile(HttpServletRequest request){
        String token = request.getHeader("token");
        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim idClaim = stringClaimMap.get("id");
        String uid = idClaim.asString();
        Long longId = Long.valueOf(uid);
        Claim userNameClaim = stringClaimMap.get("userName");
        String userName = userNameClaim.asString();

        AdminUser adminUser = adminUserService.selectAdminById(longId);

        Result result = ResultGenerator.genSuccessResult(adminUser);
        return  result;
    }
}
