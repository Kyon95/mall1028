package com.geekaca.mall.controller.fore;

import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.domain.UserAddress;
import com.geekaca.mall.exceptions.NotLoginException;
import com.geekaca.mall.service.AddressService;
import com.geekaca.mall.utils.JwtUtil;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.geekaca.mall.common.Constants.NO_LOGIN;

@RestController
@RequestMapping("/api/v1")
public class AddressController {
    @Autowired
    private AddressService addressService;

    /**
     * 获取所有地址的接口
     */
    @GetMapping("/address")
    public Result getAddressByUserId(HttpServletRequest req) {
        String token = req.getHeader("token");
        if (token == null) {
            throw new NotLoginException(NO_LOGIN, "用户未登录");
        }

        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim claim = stringClaimMap.get("id");
        String userIdStr = claim.asString();
        if (userIdStr == null) {
            throw new NotLoginException(NO_LOGIN, "用户未登录");
        }
        Long userId = Long.valueOf(userIdStr);

        List<UserAddress> addressList = addressService.getAllAddressByUserId(userId);
        return ResultGenerator.genSuccessResult(addressList);
    }

    /**
     * 增加地址
     */
    @PostMapping("/address")
    @ApiOperation(value = "添加地址", notes = "")
    public Result addAddress(@RequestBody UserAddress address, HttpServletRequest req) {
        String token = req.getHeader("token");
        if (token == null) {
            throw new NotLoginException(NO_LOGIN, "用户未登录");
        }

        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim claim = stringClaimMap.get("id");
        String userIdStr = claim.asString();
        if (userIdStr == null) {
            throw new NotLoginException(NO_LOGIN, "用户未登录");
        }
        Long userId = Long.valueOf(userIdStr);

        address.setUserId(userId);//前端传来的数据没带uerId，这里补上
        int added = addressService.addAddress(address);
        if (added > 0) {
            return ResultGenerator.genSuccessResult("添加地址成功");
        } else {
            return ResultGenerator.genFailResult("添加地址失败");
        }
    }

    /**
     * 获取某单个地址详情
     */
    @GetMapping("/address/{id}")
    @ApiOperation(value = "获取收货地址详情", notes = "传参为地址id")
    public Result getAddressById(@PathVariable("id") Long addressId, HttpServletRequest req) {
        String token = req.getHeader("token");
        if (token == null) {
            throw new NotLoginException(NO_LOGIN, "用户未登录");
        }

        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim claim = stringClaimMap.get("id");
        String userIdStr = claim.asString();
        if (userIdStr == null) {
            throw new NotLoginException(NO_LOGIN, "用户未登录");
        }
        //Long userId = Long.valueOf(userIdStr);//凭主键addressId得到某地址，甚至不需要userId

        UserAddress address = addressService.getAddressByAddressId(addressId);
        if (address != null) {
            return ResultGenerator.genSuccessResult(address);
        } else {
            return ResultGenerator.genFailResult("获取该地址失败");
        }
    }


}
