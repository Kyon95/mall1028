package com.geekaca.mall.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderItemVO {
    @ApiModelProperty("商品id")
    private Long goodsId;
    @ApiModelProperty("商品数量")
    private Integer goodsCount;
    @ApiModelProperty("商品名称")
    private String goodsName;
    @ApiModelProperty("商品图片")
    private String goodsCoverImg;
    @ApiModelProperty("商品价格")
    private Integer sellingPrice;

}
