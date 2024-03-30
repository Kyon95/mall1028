package com.geekaca.mall.controller.fore;

import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.domain.UserAddress;
import com.geekaca.mall.exceptions.NotLoginException;
import com.geekaca.mall.service.AddressService;
import com.geekaca.mall.utils.JwtUtil;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
