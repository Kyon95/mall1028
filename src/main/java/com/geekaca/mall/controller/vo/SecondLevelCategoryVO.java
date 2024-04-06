package com.geekaca.mall.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.geekaca.mall.domain.GoodsCategory;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class SecondLevelCategoryVO {

    private Long categoryId;

    /**
     * 分类级别(1-一级分类 2-二级分类 3-三级分类)
     */
    private Integer categoryLevel;

    /**
     * 父分类id
     */
    private Long parentId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 排序值(字段越大越靠前)
     */
    private Integer categoryRank;








//    private List<GoodsCategory> secondLevelCategoryVOS;
    private List<ThirdLevelCategoryVO> thirdLevelCategoryVOS;
}
