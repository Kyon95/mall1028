package com.geekaca.mall.controller.fore;

import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.controller.fore.param.CartItemParam;
import com.geekaca.mall.controller.fore.param.UpdateCartItemParam;
import com.geekaca.mall.controller.vo.ShoppingCartItemVO;
import com.geekaca.mall.service.*;
import com.geekaca.mall.utils.JwtUtil;
import com.geekaca.mall.utils.OrderPayLoad;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private OrderAddressService orderAddressService;
    @Autowired
    private GoodsInfoService goodsInfoService;
    @Autowired
    private OrderItemService orderItemService;

    @PostMapping("/shop-cart")
    public Result saveGoodsToCart(@RequestBody CartItemParam cartItemParam, @RequestHeader("Token") String token) {
        Map<String, Claim> userToken = JwtUtil.verifyToken(token);
        Claim id = userToken.get("id");
        long userId = Long.parseLong(id.asString());
        String isSaved = shoppingCartService.saveGoodToCart(cartItemParam, userId);
        if (isSaved == null) {
            return ResultGenerator.genFailResult("保存失败");
        } else {
            return ResultGenerator.genSuccessResult();
        }
    }

    @GetMapping("/shop-cart")
    public Result getCartList(@RequestHeader("Token") String token) {
        Map<String, Claim> userToken = JwtUtil.verifyToken(token);
        Claim id = userToken.get("id");
        long userId = Long.parseLong(id.asString());
        List<ShoppingCartItemVO> myCartList = shoppingCartService.getMyCartItems(userId);
        Result result = ResultGenerator.genSuccessResult();
        result.setData(myCartList);
        System.out.println(myCartList);
        return result;
    }

    // @RequestHeader 这个用的很好
    @PutMapping("/shop-cart")
    public Result updateCartItem(@RequestBody UpdateCartItemParam cartItemParam, @RequestHeader("Token") String token) {
        Map<String, Claim> userToken = JwtUtil.verifyToken(token);
        Claim id = userToken.get("id");
        long userId = Long.parseLong(id.asString());
        int isUpdated = shoppingCartService.updateCartItem(cartItemParam, userId);
        if (isUpdated < 1) {
            return ResultGenerator.genFailResult("更新失败");
        } else {
            return ResultGenerator.genSuccessResult();
        }
    }

    @GetMapping("/shop-cart/settle")
    public Result cartPayoff(@RequestParam("cartItemIds") String cartItemIds) {
        List<ShoppingCartItemVO> itemList = new ArrayList<>();
        //todo: 代码中有很多 这种System.out的打印，开发完成后想要关闭就不方便，类似的调试信息要使用logback打印
        System.out.println("cartItemIds:" + cartItemIds);
        String[] split = cartItemIds.split(",");
        System.out.println("split:" + split);
        for (int i = 0; i < split.length; i++) {
            ShoppingCartItemVO cartItem = shoppingCartService.getCartItemsByID(Long.parseLong(split[i]));
            itemList.add(cartItem);
        }
        if (itemList.size() != 0) {
            Result result = ResultGenerator.genSuccessResult();
            result.setMessage("购物车详情查询成功");
            result.setData(itemList);
            return result;
        } else {
            Result result = ResultGenerator.genFailResult("查询失败");
            return result;
        }
    }
    // 用自定义类型接收前端参数
    @PostMapping("/saveOrder")
    public Result saveOrder(@RequestBody OrderPayLoad orderPayLoad, HttpServletRequest request) {
        String token = request.getHeader("token");
        // 获取userId
        Map<String, Claim> userToken = JwtUtil.verifyToken(token);
        Claim id = userToken.get("id");
//        String username = userToken.get("userName").asString();
        long userId = Long.parseLong(id.asString());

        List<Integer> cartItemIds = orderPayLoad.getCartItemIds();
        Long addressId = orderPayLoad.getAddressId();

        String orderNo = shoppingCartService.saveOrder(userId, cartItemIds, addressId);
        Result result;
        if (orderNo != null) {
            result = ResultGenerator.genSuccessResult();
            result.setData(orderNo);
        } else {
            result = ResultGenerator.genFailResult("生成订单失败");
        }
        return result;
    }

    @GetMapping("/paySuccess")
    public Result paySuccess(@RequestParam("orderNo") String orderNo,
                             @RequestParam("payType") Integer payType) {
        int i = orderService.updateOrderStatus(orderNo, payType);
        Result result;
        if (i > 0) {
            result = ResultGenerator.genSuccessResult("支付成功");
        } else {
            result = ResultGenerator.genFailResult("支付失败");
        }
        return result;
    }
}
