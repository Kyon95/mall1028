package com.geekaca.mall.service.impl;

import com.geekaca.mall.domain.OrderItem;
import com.geekaca.mall.mapper.OrderItemMapper;
import com.geekaca.mall.service.OrderItemService;
import com.geekaca.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public int insertOrderItem(OrderItem orderItem) {
        return orderItemMapper.insertSelective(orderItem);
    }
}
