package com.geekaca.mall.service.impl;

import com.geekaca.mall.controller.fore.param.CartItemParaam;
import com.geekaca.mall.domain.GoodsInfo;
import com.geekaca.mall.domain.ShoppingCartItem;
import com.geekaca.mall.exceptions.MallException;
import com.geekaca.mall.mapper.GoodsInfoMapper;
import com.geekaca.mall.mapper.ShoppingCartItemMapper;
import com.geekaca.mall.service.ShoppingCartService;
import com.geekaca.mall.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartItemMapper shoppingCartItemMapper;
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;


    @Override
    public String saveGoodToCart(CartItemParaam cartItemParaam, Long userId) {
        ShoppingCartItem shoppingCartItem = shoppingCartItemMapper.selectByUserIdAndGoodId(userId, cartItemParaam.getGoodsId());
        if (shoppingCartItem != null) {
            throw new MallException("商品已存在！无需重复添加！");
        }
        GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(cartItemParaam.getGoodsId());
        if(goodsInfo == null){
            return null;
        }
//        int count = shoppingCartItemMapper.selectCountByUserId(userId);
//        if (count < 1) {
//            return null;
//        }
        ShoppingCartItem newCart = new ShoppingCartItem();
        newCart.setGoodsId(cartItemParaam.getGoodsId());
        newCart.setUserId(userId);
        newCart.setGoodsCount(cartItemParaam.getGoodsCount());
        int i = shoppingCartItemMapper.insertSelective(newCart);

        if (i > 0) {
            return  "success";
        }else {
            return null;
        }

    }
}
