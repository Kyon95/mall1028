package com.geekaca.mall.service.impl;

import com.geekaca.mall.controller.vo.OrderDetailVO;
import com.geekaca.mall.controller.vo.OrderItemVO;
import com.geekaca.mall.domain.Order;
import com.geekaca.mall.domain.OrderItem;
import com.geekaca.mall.mapper.OrderItemMapper;
import com.geekaca.mall.mapper.OrderMapper;
import com.geekaca.mall.service.OrderService;
import com.geekaca.mall.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class OderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

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
        return new PageResult(orders, orderCount, pageSize, pageNumber);
    }

    @Override
    public PageResult getAdminOrderList(Integer pageNumber, Integer pageSize, Integer status, String orderNo) {
        int limit = (pageNumber - 1) * pageSize;
        List<Order> orders = orderMapper.selectOrderList(limit, pageSize, status, orderNo);
        int orderCount = orderMapper.countOrders(status);
        return new PageResult(orders, orderCount, pageSize, pageNumber);
    }

    @Override
    public Order getOrderDetail(String orderNo) {
        return orderMapper.selectOrderByNo(orderNo);
        }

    @Override
    public OrderDetailVO getOrderDetailByOrderId(Long orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            return null;
        }
        List<OrderItem> orderItems = orderItemMapper.selectByOrderId(orderId);
        List<OrderItemVO> orderItemVOS = new ArrayList<>();
        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem orderItem = orderItems.get(i);
            OrderItemVO orderItemVO = new OrderItemVO();
            orderItemVO.setGoodsId(orderItem.getGoodsId());
            orderItemVO.setGoodsName(orderItem.getGoodsName());
            orderItemVO.setGoodsCount(orderItem.getGoodsCount());
            orderItemVO.setSellingPrice(orderItem.getSellingPrice());
            orderItemVO.setGoodsCoverImg(orderItem.getGoodsCoverImg());
            orderItemVOS.add(orderItemVO);
        }
        OrderDetailVO orderDetailVO = new OrderDetailVO();
        orderDetailVO.setOrderNo(order.getOrderNo());
        orderDetailVO.setTotalPrice(order.getTotalPrice());
        orderDetailVO.setPayStatus(order.getPayStatus().byteValue());
        orderDetailVO.setPayType(order.getPayType().byteValue());
        Integer payType = order.getPayType();
        switch (payType){
            case 0:
                orderDetailVO.setPayTypeString("未支付");
                break;
            case 1:
                orderDetailVO.setPayTypeString("支付宝支付");
                break;
            case 2:
                orderDetailVO.setPayTypeString("微信支付");
                break;
        }
        orderDetailVO.setPayTypeString((order.getPayType() == 1) ? "支付宝" : "微信");
        orderDetailVO.setPayTime(order.getPayTime());
        orderDetailVO.setOrderStatus(order.getOrderStatus().byteValue());
        Integer orderStatus = order.getOrderStatus();
        String orderStatusString = "";
        switch (orderStatus) {
            case -1:
                orderStatusString = "手动关闭";
                break;
            case 0:
                orderStatusString = "待支付";
                break;
            case 1:
                orderStatusString = "已支付";
                break;
            case 2:
                orderStatusString = "配货完成";
                break;
            case 3:
                orderStatusString = "出库成功";
                break;
            case 4:
                orderStatusString = "交易成功";
                break;
            case -2:
                orderStatusString = "超时关闭";
                break;
            case -3:
                orderStatusString = "商家关闭";
                break;
            default:
                orderStatusString = "未知状态";
                break;
        }
        orderDetailVO.setOrderStatusString(orderStatusString);
        orderDetailVO.setCreateTime(order.getCreateTime());
        orderDetailVO.setNewBeeMallOrderItemVOS(orderItemVOS);
        return  orderDetailVO;
    }

    @Override
    public int cancelOrder(String orderNo) {
        return orderMapper.updateOrderStaByNo(orderNo);
    }
}
