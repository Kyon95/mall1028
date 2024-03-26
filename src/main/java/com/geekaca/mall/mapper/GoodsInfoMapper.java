package com.geekaca.mall.mapper;

import com.geekaca.mall.controller.vo.IndexHotGoodsInfoVO;
import com.geekaca.mall.domain.GoodsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author katagiri
 * @description 针对表【tb_newbee_mall_goods_info】的数据库操作Mapper
 * @createDate 2024-03-27 04:11:10
 * @Entity com.geekaca.mall.domain.GoodsInfo
 */
@Mapper
public interface GoodsInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(GoodsInfo record);

    int insertSelective(GoodsInfo record);

    GoodsInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsInfo record);

    int updateByPrimaryKey(GoodsInfo record);

    /**
     * 前台  查询首页的   热销商品、新品上线、推荐商品
     * @param configType
     * @param limit
     * @return
     */
    List<IndexHotGoodsInfoVO> selectHotNewCommendGoods(@Param("configType") Integer configType,
                                                       @Param("limit")Integer limit);
}
