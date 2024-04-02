package com.geekaca.mall.service;

import com.geekaca.mall.domain.Order;

public interface OrderService {
    int insertOrder(Order order);

    int updateOrderStatus(String orderNo, Integer payType);
}
