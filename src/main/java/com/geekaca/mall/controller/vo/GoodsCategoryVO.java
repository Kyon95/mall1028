package com.geekaca.mall.controller.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @TableName tb_newbee_mall_goods_category
 */
@Data
public class GoodsCategoryVO implements Serializable {
    /**
     * 分类id
     */
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
//    private Integer categoryRank;

    /**
     * 删除标识字段(0-未删除 1-已删除)
     */
//    private Integer isDeleted;

    /**
     * 创建时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    private Date createTime;

    /**
     * 创建者id
     */
//    private Integer createUser;

    /**
     * 修改时间
     */
//    private Date updateTime;

    /**
     * 修改者id
     */
//    private Integer updateUser;


    private List<SecondLevelCategoryVO> secondLevelCategoryVOS;


}