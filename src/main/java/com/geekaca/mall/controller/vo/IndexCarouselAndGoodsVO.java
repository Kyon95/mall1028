package com.geekaca.mall.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class IndexCarouselAndGoodsVO {
//    /**
//     * 顶部轮播图
//     */
//    private List<IndexCarouselsInfoVO> carousels;

    /**
     * 轮播图下方的：
     * 热销商品、新品上线、推荐商品
     */
    private List<IndexHotGoodsInfoVO> hotGoodses;
    private List<IndexNewGoodsInfoVO> newGoodses;
    private List<IndexRecommendGoodsInfoVO> recommendGoodses;
}
