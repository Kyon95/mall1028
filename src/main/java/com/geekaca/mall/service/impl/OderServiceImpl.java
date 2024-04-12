package com.geekaca.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.geekaca.mall.controller.vo.OrderDetailForeVO;
import com.geekaca.mall.controller.vo.OrderDetailVO;
import com.geekaca.mall.controller.vo.OrderItemVO;
import com.geekaca.mall.controller.vo.OrderVO;
import com.geekaca.mall.domain.Order;
import com.geekaca.mall.domain.OrderItem;
import com.geekaca.mall.exceptions.MallException;
import com.geekaca.mall.mapper.GoodsInfoMapper;
import com.geekaca.mall.mapper.OrderItemMapper;
import com.geekaca.mall.mapper.OrderMapper;
import com.geekaca.mall.service.OrderService;
import com.geekaca.mall.utils.PageResult;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class OderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

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
        List<OrderDetailForeVO> orderDetailVOList = new ArrayList<>();

        for (Order order : orders) {
            // order 类型属性复制到orderDetailForeVO
            OrderDetailForeVO orderDetailVO = new OrderDetailForeVO();
            BeanUtil.copyProperties(order, orderDetailVO);

            List<OrderItem> newBeeMallOrderItemList = order.getNewBeeMallOrderItemVOS();
            List<OrderItemVO> newBeeMallOrderItemVOSList = new ArrayList<>();
            for (OrderItem orderItem : newBeeMallOrderItemList) {
                // OrderItem 类型属性复制到OrderItemVO
                OrderItemVO orderItemVO = new OrderItemVO();
                BeanUtil.copyProperties(orderItem, orderItemVO);
                newBeeMallOrderItemVOSList.add(orderItemVO);
            }
            orderDetailVO.setNewBeeMallOrderItemVOS(newBeeMallOrderItemVOSList);
            orderDetailVOList.add(orderDetailVO);
        }
        // 查询总记录数
        int orderCount = orderMapper.countOrders(status);
        // 返回PageResult 对象
        return new PageResult(orderDetailVOList, orderCount, pageSize, pageNumber);
    }

    @Override
    public PageResult getAdminOrderList(Integer pageNumber, Integer pageSize, Integer status, String orderNo) {
        int limit = (pageNumber - 1) * pageSize;
        List<Order> orders = orderMapper.selectOrderList(limit, pageSize, status, orderNo);
        int orderCount = orderMapper.countOrders(status);
        return new PageResult(orders, orderCount, pageSize, pageNumber);
    }

    @Override
    public OrderVO getOrderDetail(String orderNo) {
        Order order = orderMapper.selectOrderByNo(orderNo);
        OrderVO orderVO = new OrderVO();
        BeanUtil.copyProperties(order, orderVO);
        return orderVO;
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
        switch (payType) {
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
        return orderDetailVO;
    }

    @Override
    public int cancelOrder(String orderNo,Integer orderStatus ) {
        // 除了把订单状态改为取消，还要把库存恢复
        /**
         * 1. 通过orderno 找到orderId,
         * 2. 通过orderId, 找到orderItem
         * 3. 通过orderItemId 找到商品id和数量，
         * 4. 通过商品id和数量， 恢复库存
         */
        //1. 通过orderno 找到orderId,
        Long orderId = orderMapper.getOrderIdByOrderNo(orderNo);
        //2. 通过orderId, 找到orderItem
        List<OrderItem> orderItems = orderItemMapper.selectByOrderId(orderId);
        // 3. 通过orderItemId 找到商品id和数量，
        for (OrderItem orderItem : orderItems) {
            // 4. 通过商品id和数量， 恢复库存
            Integer goodsCount = orderItem.getGoodsCount();
            Long goodsId = orderItem.getGoodsId();
            int i = goodsInfoMapper.updateStock(goodsId, goodsCount);
            if (i < 0) {
                throw new MallException("更新库存失败");
            }
        }
        return orderMapper.updateOrderStaByNo(orderNo,orderStatus);
    }

    @Override
    public Boolean checkDone(Long[] ids) {
        int i = orderMapper.checkDone(Arrays.asList(ids));
        if (i > 0) {
            return  true;
        }
        return false;
    }

    @Override
    public Boolean closeOrder(Long[] ids) {
        int i = orderMapper.closeOrder(Arrays.asList(ids), -3);
        for (int j = 0; j < ids.length; j++) {
            List<OrderItem> orderItems = orderItemMapper.selectByOrderId(ids[j]);
            for (OrderItem orderItem : orderItems){
                Integer goodsCount = orderItem.getGoodsCount();
                Long goodsId = orderItem.getGoodsId();
                int i1 = goodsInfoMapper.updateStock(goodsId, goodsCount);
                if (i1 < 0) {
                    throw new MallException("更新库存失败");
                }
            }
        }
        if (i > 0) {
            return  true;
        }
        return false;
    }

    @Override
    public Boolean checkOut(Long[] ids) {
        int i = orderMapper.checkOut(Arrays.asList(ids));
        if (i > 0) {
            return  true;
        }
        return false;
    }
}
