package com.geekaca.mall.service.impl;

import com.geekaca.mall.domain.Order;
import com.geekaca.mall.mapper.OrderMapper;
import com.geekaca.mall.service.OrderService;
import com.geekaca.mall.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

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

    @Override
    public PageResult getOrders(Integer pageNumber, Integer pageSize, Integer status) {
        int limit = (pageNumber - 1) * pageSize;
        List<Order> orders = orderMapper.selectAllOrders(limit, pageSize, status);
        // 查询总记录数
        int orderCount = orderMapper.countOrders(status);
        // 返回PageResult 对象
        return new PageResult(orders, orderCount,pageSize,pageNumber);
    }
}
