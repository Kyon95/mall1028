package com.geekaca.mall.controller.fore;

import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.controller.fore.param.CartItemParam;
import com.geekaca.mall.controller.fore.param.UpdateCartItemParam;
import com.geekaca.mall.controller.vo.ShoppingCartItemVO;
import com.geekaca.mall.service.ShoppingCartService;
import com.geekaca.mall.utils.JwtUtil;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/shop-cart")
    public Result saveGoodsToCart(@RequestBody CartItemParam cartItemParam, @RequestHeader("Token")String token) {
        Map<String, Claim> userToken = JwtUtil.verifyToken(token);
        Claim id = userToken.get("id");
        long userId = Long.parseLong(id.asString());
        String isSaved = shoppingCartService.saveGoodToCart(cartItemParam, userId);
        if (isSaved == null) {
            return ResultGenerator.genFailResult("保存失败");
        }else {
            return ResultGenerator.genSuccessResult();
        }
    }

    @GetMapping("/shop-cart")
    public Result getCartList(@RequestHeader("Token")String token) {
        Map<String, Claim> userToken = JwtUtil.verifyToken(token);
        Claim id = userToken.get("id");
        long userId = Long.parseLong(id.asString());
        List<ShoppingCartItemVO> myCartList = shoppingCartService.getMyCartItems(userId);
        Result result = ResultGenerator.genSuccessResult();
        result.setData(myCartList);
        System.out.println(myCartList);
        return result;
    }

    @PutMapping("/shop-cart")
    public Result updateCartItem(@RequestBody UpdateCartItemParam cartItemParam, @RequestHeader("Token")String token) {
        Map<String, Claim> userToken = JwtUtil.verifyToken(token);
        Claim id = userToken.get("id");
        long userId = Long.parseLong(id.asString());
        int isUpdated = shoppingCartService.updateCartItem(cartItemParam, userId);
        if (isUpdated < 1) {
            return ResultGenerator.genFailResult("更新失败");
        }else {
            return ResultGenerator.genSuccessResult();
        }
    }
}
