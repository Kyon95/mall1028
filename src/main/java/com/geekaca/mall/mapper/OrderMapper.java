package com.geekaca.mall.mapper;

import com.geekaca.mall.domain.Order;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Kyon_Yjx
* @description 针对表【tb_newbee_mall_order(订单整体信息)】的数据库操作Mapper
* @createDate 2024-03-31 15:42:14
* @Entity com.geekaca.mall.domain.Order
*/
@Mapper
public interface OrderMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

}