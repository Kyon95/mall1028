package com.geekaca.mall.service.impl;

import com.geekaca.mall.domain.Order;
import com.geekaca.mall.mapper.OrderMapper;
import com.geekaca.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class OderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int insertOrder(Order order) {
        return orderMapper.insertSelective(order);
    }

    @Override
    public int updateOrderStatus(String orderNo, Integer payType) {

        Long orderId = orderMapper.getOrderIdByOrderNo(orderNo);

        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setPayType(payType);
        order.setOrderId(orderId);
        order.setPayStatus(1);
        order.setOrderStatus(1);
        order.setPayTime(new Date(System.currentTimeMillis()));
        return orderMapper.updateByPrimaryKeySelective(order);
    }
}
