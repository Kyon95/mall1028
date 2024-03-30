package com.geekaca.mall.mapper;

import com.geekaca.mall.domain.UserAddress;

import java.util.List;

/**
 * @author katagiri
 * @description 针对表【tb_newbee_mall_user_address(收货地址表)】的数据库操作Mapper
 * @createDate 2024-03-30 21:02:56
 * @Entity com.geekaca.mall.domain.UserAddress
 */
public interface UserAddressMapper {

    int deleteByPrimaryKey(Long id);

    /**
     * 添加地址
     */
    int insert(UserAddress record);

    int insertSelective(UserAddress record);

    UserAddress selectByPrimaryKey(Long id);

    /**
     * 更新地址
     */
    int updateByPrimaryKeySelective(UserAddress record);

    int updateByPrimaryKey(UserAddress record);

    /**
     * 根据用户id获取所有收货地址
     */
    List<UserAddress> selectAllAddressByUid(Long userId);

    /**
     * 找到默认收货地址
     */
    UserAddress selectDefaultAddressByUid(Long userId);
}
