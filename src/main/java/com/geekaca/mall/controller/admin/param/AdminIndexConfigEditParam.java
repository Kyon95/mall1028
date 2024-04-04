package com.geekaca.mall.controller.admin.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AdminIndexConfigEditParam {
    @NotNull(message = "configId不能为空")
    private Long configId;
    @NotEmpty(message = "商品名称不能为空")
    private String configName;
    @NotNull(message = "排序值不能为空")
    private Integer configRank;
    @NotNull(message = "配置类型不能为空")
    private Integer configType;
    @NotNull(message = "商品编号不能为空")
    private Long goodsId;

    private String redirectUrl;

}
