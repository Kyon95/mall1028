package com.geekaca.mall.service;

import com.geekaca.mall.controller.fore.param.CartItemParaam;
import com.geekaca.mall.controller.vo.ShoppingCartItemVO;

import java.util.List;

public interface ShoppingCartService {

    String saveGoodToCart(CartItemParaam cartItemParaam,Long userId);

    public List<ShoppingCartItemVO> getMyCartItems(Long userId);

}
