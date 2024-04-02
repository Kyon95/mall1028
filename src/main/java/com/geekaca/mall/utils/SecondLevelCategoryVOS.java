package com.geekaca.mall.utils;

import com.geekaca.mall.domain.GoodsCategory;
import lombok.Data;

import java.util.List;
//todo:这种不加格式化，太混乱
@Data
public class SecondLevelCategoryVOS extends GoodsCategory {    private List<GoodsCategory> thirdLevelCategoryVOS;

}
