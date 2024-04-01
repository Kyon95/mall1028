package com.geekaca.mall.controller.fore.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateCartItemParam implements Serializable {
    @ApiModelProperty("购物项主键id")
    private Long cartItemId;
    @ApiModelProperty("商品数量")
    private Integer goodsCount;
}
