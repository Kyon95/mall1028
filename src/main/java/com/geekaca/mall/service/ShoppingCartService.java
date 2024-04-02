package com.geekaca.mall.service;

import com.geekaca.mall.controller.fore.param.CartItemParam;
import com.geekaca.mall.controller.fore.param.UpdateCartItemParam;
import com.geekaca.mall.controller.vo.ShoppingCartItemVO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ShoppingCartService {

    String saveGoodToCart(CartItemParam cartItemParam, Long userId);

    public List<ShoppingCartItemVO> getMyCartItems(Long userId);


    public int updateCartItem(UpdateCartItemParam updateCartItemParam, Long userId);


    ShoppingCartItemVO getCartItemsByID(long parseLong);

    int deleteCartItem(Long cartItemId);

}
