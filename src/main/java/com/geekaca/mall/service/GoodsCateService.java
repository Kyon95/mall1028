package com.geekaca.mall.service;

import com.geekaca.mall.domain.GoodsCategory;
import com.geekaca.mall.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface GoodsCateService {
    PageResult findAllGoodsCategory(Integer pageNumber, Integer pageSize, Integer categoryLevel, Integer parentId);

    Long findParentId(Long categoryId);

    GoodsCategory findCatgoryById(Long categoryId);

    int updateGoodsCategory(GoodsCategory goodsCategory);

    int saveGoodsCategory(GoodsCategory goodsCategory);

    int deleteGoodsCategory(Long categoryId);

    int deleteGoodsCategoryByIds(List<Integer> ids);

    List<List> findAllCatoriesAndSubCatories();
}
