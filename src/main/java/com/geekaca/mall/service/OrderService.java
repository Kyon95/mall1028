package com.geekaca.mall.service;

import com.geekaca.mall.controller.vo.OrderDetailVO;
import com.geekaca.mall.controller.vo.OrderVO;
import com.geekaca.mall.domain.Order;
import com.geekaca.mall.utils.PageResult;

public interface OrderService {
    int insertOrder(Order order);

    int updateOrderStatus(String orderNo, Integer payType);

    PageResult getOrders(Integer pageNumber, Integer pageSize, Integer status);

    PageResult getAdminOrderList(Integer pageNumber, Integer pageSize, Integer status,String orderNo);

    OrderVO getOrderDetail(String orderNo);


    public OrderDetailVO getOrderDetailByOrderId(Long orderId);

    int cancelOrder(String orderNo,Integer orderStatus);

    Boolean checkDone(Long[] ids);

    Boolean closeOrder(Long[] ids);

    Boolean checkOut(Long[] ids);
}
