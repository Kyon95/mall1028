package com.geekaca.mall.controller.fore.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CartItemParaam implements Serializable {
    @ApiModelProperty("商品数量")
    private Integer goodsCount;
    @ApiModelProperty("商品id")
    private Long goodsId;
}
