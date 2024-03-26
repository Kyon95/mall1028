package com.geekaca.mall.controller.vo;

import lombok.Data;

/**
 * 新品上线value object
 */
@Data
public class IndexNewGoodsInfoVO {
    private String goodsCoverImg;
    private Long goodsId;
    private String goodsIntro;
    private String goodsName;
    private Integer sellingPrice;
    private String tag;
}
