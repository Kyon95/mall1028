package com.geekaca.mall.service;

import com.geekaca.mall.domain.Order;
import com.geekaca.mall.utils.PageResult;

public interface OrderService {
    int insertOrder(Order order);

    int updateOrderStatus(String orderNo, Integer payType);

    PageResult getOrders(Integer pageNumber, Integer pageSize, Integer status);

    PageResult getAdminOrderList(Integer pageNumber, Integer pageSize, Integer status,String orderNo);
}
