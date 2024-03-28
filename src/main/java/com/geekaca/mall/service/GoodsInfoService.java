package com.geekaca.mall.service;

import com.geekaca.mall.controller.vo.GoodsDetailVO;
import com.geekaca.mall.controller.vo.IndexHotGoodsInfoVO;
import com.geekaca.mall.controller.vo.IndexNewGoodsInfoVO;
import com.geekaca.mall.controller.vo.IndexRecommendGoodsInfoVO;
import com.geekaca.mall.domain.GoodsInfo;
import com.geekaca.mall.utils.PageResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface GoodsInfoService {
    /**
     * 前台 获取热销商品
     */
    List<IndexHotGoodsInfoVO> getHotGoods();

    /**
     * 前台 获取新品上线
     */
    List<IndexNewGoodsInfoVO> getNewGoods();

    /**
     * 前台 获取推荐商品
     */
    List<IndexRecommendGoodsInfoVO> getRecommendGoods();





    PageResult findAllGoods(Integer pageNo, Integer pageSize,String goodsName);


    /**
     * 前台  点击商品  获取商品详情
     * 凭goods_id获取 商品信息
     */
    GoodsDetailVO getById(Long goodsId);

    int insertGood(GoodsInfo goodsInfo);

    GoodsInfo  findGoodById(Long id);

    int updateGood(GoodsInfo goodsInfo);

    int setGoodStatus(Integer status, List ids);
}
