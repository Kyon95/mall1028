package com.geekaca.mall.controller.vo;

import lombok.Data;

/**
 * 热销商品value object
 */
@Data
public class IndexHotGoodsInfoVO {
    private String goodsCoverImg;
    private Long goodsId;
    private String goodsIntro;
    private String goodsName;
    private Integer sellingPrice;
    private String tag;
}
