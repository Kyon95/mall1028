package com.geekaca.mall.service;

import com.geekaca.mall.controller.admin.param.AdminCarouselParam;
import com.geekaca.mall.controller.admin.param.BatchIdParam;
import com.geekaca.mall.domain.Carousel;
import com.geekaca.mall.utils.PageResult;

import java.util.List;

public interface AdminCarouselService {


    int deleteCarousel(BatchIdParam batchIdParam);

    PageResult getCarousels(Integer pageNumber, Integer pageSize);

    int saveCarousel(AdminCarouselParam adminCarouselParam);
}
