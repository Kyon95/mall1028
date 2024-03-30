package com.geekaca.mall.utils;

import com.geekaca.mall.domain.GoodsCategory;
import lombok.Data;

import java.util.List;

@Data
public class SecondLevelCategoryVOS extends GoodsCategory {    private List<GoodsCategory> thirdLevelCategoryVOS;

}
