package com.geekaca.mall.service;

import com.geekaca.mall.controller.fore.param.CartItemParaam;

public interface ShoppingCartService {

    public String saveGoodToCart(CartItemParaam cartItemParaam,Long userId);

}
