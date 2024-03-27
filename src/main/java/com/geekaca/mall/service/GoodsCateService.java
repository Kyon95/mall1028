package com.geekaca.mall.service;

import com.geekaca.mall.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

public interface GoodsCateService {
    PageResult findAllGoodsCategory(Integer pageNumber, Integer pageSize, Integer categoryLevel, Integer parentId);
}
