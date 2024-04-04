package com.geekaca.mall.mapper;

import com.geekaca.mall.domain.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Kyon_Yjx
* @description 针对表【tb_newbee_mall_order_item】的数据库操作Mapper
* @createDate 2024-03-31 15:42:14
* @Entity com.geekaca.mall.domain.OrderItem
*/
@Mapper
public interface OrderItemMapper {

    int deleteByPrimaryKey(Long id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    List<OrderItem>selectByOrderId(Long orderId);

}
