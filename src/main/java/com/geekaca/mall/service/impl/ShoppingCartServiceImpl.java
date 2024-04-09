package com.geekaca.mall.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.geekaca.mall.controller.fore.param.CartItemParam;
import com.geekaca.mall.controller.fore.param.UpdateCartItemParam;
import com.geekaca.mall.controller.vo.ShoppingCartItemVO;
import com.geekaca.mall.domain.*;
import com.geekaca.mall.exceptions.MallException;
import com.geekaca.mall.mapper.GoodsInfoMapper;
import com.geekaca.mall.mapper.ShoppingCartItemMapper;
import com.geekaca.mall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartItemMapper shoppingCartItemMapper;
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsInfoService goodsInfoService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private OrderAddressService orderAddressService;

    @Override
    public String saveGoodToCart(CartItemParam cartItemParam, Long userId) {
        ShoppingCartItem shoppingCartItem = shoppingCartItemMapper.selectByUserIdAndGoodId(userId, cartItemParam.getGoodsId());
        if (shoppingCartItem != null) {
            throw new MallException("商品已存在！无需重复添加！");
        }
        GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(cartItemParam.getGoodsId());
        if (goodsInfo == null) {
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
            return "success";
        } else {
            return null;
        }

    }

    @Override
    public List<ShoppingCartItemVO> getMyCartItems(Long userId) {
        List<ShoppingCartItemVO> goodsCartList = shoppingCartItemMapper.getGoodsListByUserId(userId);
        return goodsCartList;
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

    @Override
    @Transactional
    public String saveOrder(long userId, List<Integer> cartItemIds, Long addressId) {
        // 雪花算法获取orderNO
        Snowflake snowflake = IdUtil.getSnowflake();
        String orderNo = String.valueOf(snowflake.nextId());
        //  写入order表，并返回order_id
        Integer totalPrice = 0;
        // 创建存放 商品id与数量 的map 集合 goodsMaps
        List<Map<Long, Integer>> goodMaps = new ArrayList<>();
        for (int i = 0; i < cartItemIds.size(); i++) {
            long cartItemId = Long.parseLong(cartItemIds.get(i).toString());
            ShoppingCartItemVO cartItem = getCartItemsByID(cartItemId);
            Map<Long, Integer> goodMap = new HashMap<>();
            goodMap.put(cartItem.getGoodsId(), cartItem.getGoodsCount());
            // 生成订单同步减库存
            int affected = goodsInfoMapper.updateStockById(cartItem.getGoodsId(), cartItem.getGoodsCount());
            if (affected < 0) {
                throw new MallException("库存不足！");
            }
            goodMaps.add(goodMap);
            Integer sellingPrice = cartItem.getSellingPrice();
            Integer itemTotlePrice = sellingPrice * cartItem.getGoodsCount();
            totalPrice = totalPrice + itemTotlePrice;
            // 生成订单后要把购物车清空，设cartItem 状态设为 is_deleted=1, 逻辑删除
            deleteCartItem(cartItem.getCartItemId());
        }
        // 上一步准备的数据用来创建order对象，写入数据库
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setTotalPrice(totalPrice);
        int i = orderService.insertOrder(order);
        if (i<0){
            throw  new MallException("订单新增失败");
        }

        // 写入 order_item 表
        Long orderId = order.getOrderId();
        for (Map<Long, Integer> goodMap : goodMaps) {
            Long goodsId = goodMap.keySet().iterator().next();
            GoodsInfo good = goodsInfoService.findGoodById(goodsId);
            Integer goodsCount = goodMap.get(goodsId);
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setGoodsId(goodsId);
            orderItem.setGoodsName(good.getGoodsName());
            orderItem.setGoodsCoverImg(good.getGoodsCoverImg());
            orderItem.setSellingPrice(good.getSellingPrice());
            orderItem.setGoodsCount(goodsCount);
            int i1 = orderItemService.insertOrderItem(orderItem);
            if (i1<0){
                throw  new MallException("order_item 表写入失败");
            }
        }
        // 写入order_address表，主键为 order_id
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
        if(j<0){
            throw new MallException("order_address 表写入失败");
        }
        return  orderNo;
    }
}
