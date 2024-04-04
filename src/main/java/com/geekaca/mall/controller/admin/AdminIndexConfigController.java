package com.geekaca.mall.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.controller.admin.param.AdminIndexConfigAddParam;
import com.geekaca.mall.controller.admin.param.AdminIndexConfigEditParam;
import com.geekaca.mall.controller.admin.param.AdminIndexConfigPageParam;
import com.geekaca.mall.domain.IndexConfig;
import com.geekaca.mall.exceptions.NotLoginException;
import com.geekaca.mall.service.AdminIndexConfigService;
import com.geekaca.mall.utils.JwtUtil;
import com.geekaca.mall.utils.PageResult;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

import static com.geekaca.mall.common.Constants.NO_LOGIN;

@RestController
@RequestMapping("/manage-api/v1")
@Api(value = "v1", tags = "8-4.后台管理系统首页配置模块接口")
@Slf4j
public class AdminIndexConfigController {
    @Autowired
    private AdminIndexConfigService indexConfigService;

    /**
     * 列表
     */
    @GetMapping("/indexConfigs")
    @ApiOperation(value = "首页配置列表", notes = "首页配置列表")
    public Result list(@RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
                       @RequestParam(required = false) @ApiParam(value = "每页条数") Integer pageSize,
                       @RequestParam(required = false)
                       @ApiParam(value = "3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐") Integer configType,
                       HttpServletRequest req) {
        String token = req.getHeader("token");
        if (token == null) {
            throw new NotLoginException(NO_LOGIN, "管理员未登录");
        }

        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim claim = stringClaimMap.get("id");
        String adminUserIdStr = claim.asString();
        if (adminUserIdStr == null) {
            throw new NotLoginException(NO_LOGIN, "管理员未登录");
        }
        Long adminUserId = Long.valueOf(adminUserIdStr);
        log.info("adminUserId:{}", adminUserId.toString());


        if (pageNumber == null || pageNumber < 1 || pageSize == null || pageSize < 10) {
            return ResultGenerator.genFailResult("分页参数异常！");
        }
        AdminIndexConfigPageParam indexConfigPageParam =
                new AdminIndexConfigPageParam(pageNumber, pageSize, configType);
        PageResult indexConfigPage = indexConfigService.getIndexConfigByConfigType(indexConfigPageParam);
        return ResultGenerator.genSuccessResult(indexConfigPage);
    }

    /**
     * 添加
     */
    @PostMapping("/indexConfigs")
    @ApiOperation(value = "新增首页配置项", notes = "新增首页配置项")
    public Result save(@RequestBody @Valid AdminIndexConfigAddParam indexConfigAddParam,
                       HttpServletRequest req) {
        String token = req.getHeader("token");
        if (token == null) {
            throw new NotLoginException(NO_LOGIN, "管理员未登录");
        }

        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim claim = stringClaimMap.get("id");
        String adminUserIdStr = claim.asString();
        if (adminUserIdStr == null) {
            throw new NotLoginException(NO_LOGIN, "管理员未登录");
        }
        Integer adminUserId = Integer.valueOf(adminUserIdStr);
        log.info("adminUserId:{}", adminUserId.toString());


        IndexConfig indexConfig = new IndexConfig();
        BeanUtil.copyProperties(indexConfigAddParam, indexConfig);
        indexConfig.setCreateUser(adminUserId);//前端传来的参数没有管理员id，这里补上
        indexConfig.setUpdateUser(adminUserId);//且首次新建时，create_user和update_user是同一人

        int saved = indexConfigService.saveIndexConfig(indexConfig);
        if (saved > 0) {
            return ResultGenerator.genSuccessResult("增加成功");
        } else {
            return ResultGenerator.genFailResult("增加失败");
        }
    }

    /**
     * 某个配置详情
     */
    @GetMapping("/indexConfigs/{id}")
    @ApiOperation(value = "获取单条首页配置项信息", notes = "根据id查询")
    public Result info(@PathVariable("id") Long configId) {
        IndexConfig config = indexConfigService.getIndexConfigById(configId);
        if (config == null) {
            return ResultGenerator.genFailResult("未查询到数据");
        }
        return ResultGenerator.genSuccessResult(config);
    }

    /**
     * 修改
     */
    @PutMapping("/indexConfigs")
    @ApiOperation(value = "修改首页配置项", notes = "修改首页配置项")
    public Result update(@RequestBody @Valid AdminIndexConfigEditParam indexConfigEditParam,
                         HttpServletRequest req) {
        String token = req.getHeader("token");
        if (token == null) {
            throw new NotLoginException(NO_LOGIN, "管理员未登录");
        }

        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim claim = stringClaimMap.get("id");
        String adminUserIdStr = claim.asString();
        if (adminUserIdStr == null) {
            throw new NotLoginException(NO_LOGIN, "管理员未登录");
        }
        Integer adminUserId = Integer.valueOf(adminUserIdStr);

        IndexConfig indexConfig = new IndexConfig();
        BeanUtil.copyProperties(indexConfigEditParam, indexConfig);
        //前端传来的参数没有管理员id，这里补上，用以修改update_user
        indexConfig.setUpdateUser(adminUserId);

        int edited = indexConfigService.editIndexConfig(indexConfig);
        if (edited > 0) {
            return ResultGenerator.genSuccessResult("修改成功");
        } else {
            return ResultGenerator.genFailResult("修改失败");
        }
    }
}
