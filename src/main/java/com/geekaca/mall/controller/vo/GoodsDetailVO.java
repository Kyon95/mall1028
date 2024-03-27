package com.geekaca.mall.controller.vo;

import lombok.Data;

import java.util.List;

/**
 * 商品详情value object
 */
@Data
public class GoodsDetailVO {
    private List goodsCarouselList;
    private String goodsCoverImg;
    private String goodsDetailContent;
    private Long goodsId;
    private String goodsIntro;
    private String goodsName;
    private Integer originalPrice;
    private Integer sellingPrice;
    private String tag;
}
