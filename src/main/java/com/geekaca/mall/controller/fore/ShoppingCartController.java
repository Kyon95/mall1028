package com.geekaca.mall.controller.fore;

import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.controller.fore.param.CartItemParam;
import com.geekaca.mall.controller.fore.param.UpdateCartItemParam;
import com.geekaca.mall.controller.vo.ShoppingCartItemVO;
import com.geekaca.mall.domain.Order;
import com.geekaca.mall.domain.OrderAddress;
import com.geekaca.mall.domain.UserAddress;
import com.geekaca.mall.service.AddressService;
import com.geekaca.mall.service.OrderAddressService;
import com.geekaca.mall.service.OrderService;
import com.geekaca.mall.service.ShoppingCartService;
import com.geekaca.mall.utils.JwtUtil;
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

    @GetMapping("/shop-cart/settle")
    public Result cartPayoff(@RequestParam("cartItemIds") String cartItemIds) {
        List<ShoppingCartItemVO> itemList = new ArrayList<>();
        System.out.println("cartItemIds:" + cartItemIds);
        String[] split = cartItemIds.split(",");
        System.out.println("split:" + split);
        for (int i = 0; i < split.length; i++) {
            ShoppingCartItemVO cartItem = shoppingCartService.getCartItemsByID(Long.parseLong(split[i]));
            System.out.println(cartItem);
            itemList.add(cartItem);

        }
        if (itemList.size() != 0) {
            Result result = ResultGenerator.genSuccessResult();
            result.setMessage("购物车详情查询成功");
            result.setData(itemList);
            return result;
        } else {
            Result result = ResultGenerator.genFailResult("查询失败");
            result.setResultCode(500);
            return result;
        }
    }

    @PostMapping("/saveOrder")
    public Result saveOrder(@RequestBody Map params, HttpServletRequest request){
        String token = request.getHeader("token");
        // 获取userId
        Map<String, Claim> userToken = JwtUtil.verifyToken(token);
        Claim id = userToken.get("id");
//        String username = userToken.get("userName").asString();
        long userId = Long.parseLong(id.asString());

//        Claim idClaim = stringClaimMap.get("id");
//        String uid = idClaim.asString();
//        Long longId = Long.valueOf(uid);
//        Claim userNameClaim = stringClaimMap.get("userName");
//        String userName = userNameClaim.asString();

        List<Integer> cartItemIds = (ArrayList<Integer>) params.get("cartItemIds");
        Long addressId = Long.valueOf(params.get("addressId").toString());
        System.out.println(cartItemIds);
        long l = System.currentTimeMillis();
        String timestamp = String.valueOf(l);

        Integer totalPrice =0;
        //  写入订单表，并返回order_id
        for (int i = 0; i < cartItemIds.size(); i++) {
            ShoppingCartItemVO cartItem =
                    shoppingCartService.getCartItemsByID(Long.parseLong(cartItemIds.get(i).toString()));
            Integer sellingPrice = cartItem.getSellingPrice();
            Integer itemTotlePrice = sellingPrice * cartItem.getGoodsCount();
            totalPrice =totalPrice+ itemTotlePrice;
            // 把cartItem 状态设为 is_deleted
            shoppingCartService.deleteCartItem(cartItem.getCartItemId());

        }
        Order order = new Order();
        order.setOrderNo(timestamp);
        order.setUserId(userId);
        order.setTotalPrice(totalPrice);

        int i = orderService.insertOrder(order);


        // 写入订单地址表，主键为 order_id
        UserAddress address = addressService.getAddressByAddressId(addressId);

        OrderAddress orderAddress = new OrderAddress();
        orderAddress.setOrderId(order.getOrderId());
        orderAddress.setUserName(address.getUserName());
        orderAddress.setUserPhone(address.getUserPhone());
        orderAddress.setProvinceName(address.getProvinceName());
        orderAddress.setCityName(address.getCityName());
        orderAddress.setRegionName(address.getRegionName());
        orderAddress.setDetailAddress(address.getDetailAddress());

        int j = orderAddressService.insertOrderAddress(orderAddress);


        Result res = new Result();
        res.setData(timestamp);
        res.setMessage("SUCCESS");
        res.setResultCode(200);

        return res;
    }

    @GetMapping("/paySuccess")
    public Result paySuccess(@RequestParam("orderNo") String orderNo,
                             @RequestParam("payType") Integer payType  ) {



        int i = orderService.updateOrderStatus(orderNo, payType);
        if(i>0){
            Result result = ResultGenerator.genSuccessResult("支付成功");
                return result;
        }else {
            Result result = ResultGenerator.genFailResult("支付失败");
            return result;
        }
    }
}
