package com.geekaca.mall.service;

import com.geekaca.mall.domain.GoodsCategory;
import com.geekaca.mall.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

public interface GoodsCateService {
    PageResult findAllGoodsCategory(Integer pageNumber, Integer pageSize, Integer categoryLevel, Integer parentId);

    Long findParentId(Long categoryId);

    GoodsCategory findCatgoryById(Long categoryId);
}
