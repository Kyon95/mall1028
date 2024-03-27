package com.geekaca.mall.mapper;

import com.geekaca.mall.controller.vo.IndexHotGoodsInfoVO;
import com.geekaca.mall.domain.GoodsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Peter
 * @description 针对表【tb_newbee_mall_goods_info】的数据库操作Mapper
 * @createDate 2024-03-26 17:55:17
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

    List<GoodsInfo> findGoodsList(@Param("limit") Integer limit, @Param("pageSize") Integer pageSize,
                                  @Param("goodsName") String goodsName);

    int findGoodsCount(@Param("goodsName") String goodsName);


    /**
     * 前台  点击商品  获取商品详情
     * 凭goods_id获取 商品信息
     * @param goodsId
     * @return
     */
    GoodsInfo selectDetailById(Long goodsId);

}
