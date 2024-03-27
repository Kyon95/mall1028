package com.geekaca.mall.mapper;

import com.geekaca.mall.domain.MallUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author Kyon_Yjx
* @description 针对表【tb_newbee_mall_user】的数据库操作Mapper
* @createDate 2024-03-27 14:47:57
* @Entity com.geekaca.mall.domain.MallUser
*/
@Mapper
public interface MallUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(MallUser record);

    int insertSelective(MallUser record);

    MallUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MallUser record);

    int updateByPrimaryKey(MallUser record);

    MallUser checkLogin(@Param("loginName")String loginName, @Param("passwordMd5")String passwordMd5);

}
