package com.geekaca.mall.service.impl;

import com.geekaca.mall.controller.fore.param.CartItemParam;
import com.geekaca.mall.controller.fore.param.UpdateCartItemParam;
import com.geekaca.mall.controller.vo.ShoppingCartItemVO;
import com.geekaca.mall.domain.GoodsInfo;
import com.geekaca.mall.domain.ShoppingCartItem;
import com.geekaca.mall.exceptions.MallException;
import com.geekaca.mall.mapper.GoodsInfoMapper;
import com.geekaca.mall.mapper.ShoppingCartItemMapper;
import com.geekaca.mall.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartItemMapper shoppingCartItemMapper;
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;


    @Override
    public String saveGoodToCart(CartItemParam cartItemParam, Long userId) {
        ShoppingCartItem shoppingCartItem = shoppingCartItemMapper.selectByUserIdAndGoodId(userId, cartItemParam.getGoodsId());
        if (shoppingCartItem != null) {
            throw new MallException("商品已存在！无需重复添加！");
        }
        GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(cartItemParam.getGoodsId());
        if(goodsInfo == null){
            return null;
        }
//        int count = shoppingCartItemMapper.selectCountByUserId(userId);
//        if (count < 1) {
//            return null;
//        }
        ShoppingCartItem newCart = new ShoppingCartItem();
        newCart.setGoodsId(cartItemParam.getGoodsId());
        newCart.setUserId(userId);
        newCart.setGoodsCount(cartItemParam.getGoodsCount());
        int i = shoppingCartItemMapper.insertSelective(newCart);

        if (i > 0) {
            return  "success";
        }else {
            return null;
        }

    }

    @Override
    public List<ShoppingCartItemVO> getMyCartItems(Long userId) {
        List<ShoppingCartItemVO> goodsCartList = shoppingCartItemMapper.getGoodsListByUserId(userId);
        return  goodsCartList;
    }

    @Override
    public int updateCartItem(UpdateCartItemParam updateCartItemParam, Long userId) {
        ShoppingCartItem shoppingCartItem = shoppingCartItemMapper.selectByPrimaryKey(updateCartItemParam.getCartItemId());
        if (shoppingCartItem == null) {
            throw new MallException("购物车并没有此商品！");
        }
        if (!shoppingCartItem.getUserId().equals(userId)) {
            throw new MallException("非法操作！");
        }
        shoppingCartItem.setGoodsCount(updateCartItemParam.getGoodsCount());
        return shoppingCartItemMapper.updateByPrimaryKeySelective(shoppingCartItem);

    }

    @Override
    public ShoppingCartItemVO getCartItemsByID(long parseLong) {
        ShoppingCartItemVO shoppingCartItemVO = shoppingCartItemMapper.getCartItemsByID(parseLong);
        return shoppingCartItemVO;
    }

    @Override
    public int deleteCartItem(Long cartItemId) {
        return shoppingCartItemMapper.logicDeleteByPrimaryKey(cartItemId);

    }
}
