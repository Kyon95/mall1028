package com.geekaca.mall.controller.vo;

import com.geekaca.mall.domain.MallCarousel;
import lombok.Data;

import java.util.List;

@Data
public class IndexCarouselAndGoodsVO {

    /**
     * 轮播图下方的：
     * 热销商品、新品上线、推荐商品
     * 顶部轮播图完成——3.28
     */
    private List<IndexHotGoodsInfoVO> hotGoodses;
    private List<IndexNewGoodsInfoVO> newGoodses;
    private List<IndexRecommendGoodsInfoVO> recommendGoodses;
    private List<MallCarousel> carousels;
}
