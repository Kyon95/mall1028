package com.geekaca.mall.controller.fore;

import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.controller.fore.param.CartItemParaam;
import com.geekaca.mall.service.ShoppingCartService;
import com.geekaca.mall.utils.JwtUtil;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/shop-cart")
    public Result saveGoodsToCart(@RequestBody CartItemParaam cartItemParaam,@RequestHeader("Token")String token) {
        Map<String, Claim> userToken = JwtUtil.verifyToken(token);
        Claim id = userToken.get("id");
        long userId = Long.parseLong(id.asString());
        String isSaved = shoppingCartService.saveGoodToCart(cartItemParaam, userId);
        if (isSaved == null) {
            return ResultGenerator.genFailResult("保存失败");
        }else {
            return ResultGenerator.genSuccessResult();
        }
    }


}