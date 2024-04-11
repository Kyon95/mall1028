package com.geekaca.mall.controller.admin;

import com.geekaca.mall.controller.admin.param.BatchIdParam;
import com.geekaca.mall.service.MallUserService;
import com.geekaca.mall.utils.PageResult;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/manage-api/v1")
public class UserManagerController {
    @Autowired
    private MallUserService userService;

    @GetMapping("/users")
    @ResponseBody
    public Result listUser(@RequestParam(required = false) Integer pageNumber,
                           @RequestParam(required = false) Integer pageSize){
        PageResult userList = userService.getUserList(pageNumber, pageSize);
        Result result = ResultGenerator.genSuccessResult(userList);
        return result;
    }

    @PutMapping("/users/{lockedFlag}")
    @ResponseBody
    public Result banUser(@RequestBody BatchIdParam batchIdParam, @PathVariable Integer lockedFlag){
        boolean isBanned = userService.banUser(batchIdParam,lockedFlag);
        if (isBanned) {
            return ResultGenerator.genSuccessResult();
        }else {
            return ResultGenerator.genFailResult("修改状态失败");
        }
    }
}
