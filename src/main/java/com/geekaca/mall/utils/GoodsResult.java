package com.geekaca.mall.utils;

import com.geekaca.mall.domain.GoodsCategory;
import com.geekaca.mall.domain.GoodsInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsResult implements Serializable {
    // 孙类别
    private GoodsCategory thirdCategory;
    // 子类别
    private GoodsCategory secondCategory;
    // 父类别
    private GoodsCategory firstCategory;
    // 商品信息
    private GoodsInfo goods;

    public GoodsResult(GoodsInfo goods, GoodsCategory firstCategory, GoodsCategory secondCategory, GoodsCategory thirdCategory
    ) {
        this.thirdCategory = thirdCategory;
        this.secondCategory = secondCategory;
        this.firstCategory = firstCategory;
        this.goods = goods;
    }
}
