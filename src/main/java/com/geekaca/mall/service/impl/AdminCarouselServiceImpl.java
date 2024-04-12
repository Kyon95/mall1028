package com.geekaca.mall.service.impl;

import com.geekaca.mall.controller.admin.param.AdminCarouselParam;
import com.geekaca.mall.controller.admin.param.BatchIdParam;
import com.geekaca.mall.domain.Carousel;
import com.geekaca.mall.mapper.CarouselMapper;
import com.geekaca.mall.service.AdminCarouselService;
import com.geekaca.mall.service.AdminUserService;
import com.geekaca.mall.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCarouselServiceImpl implements AdminCarouselService {
    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public int deleteCarousel(BatchIdParam batchIdParam) {
        Long[] ids = batchIdParam.getIds();
        return carouselMapper.deleteByPrimaryKeys(ids);
    }

    @Override
    public PageResult getCarousels(Integer pageNumber, Integer pageSize) {
        // 获取总记录数
        Integer total = carouselMapper.getTotalCarousels();

        // 获取分页数据
        Integer limit = (pageNumber-1)*pageSize;

        List<Carousel> carousels = carouselMapper.getAllCarousels(limit, pageSize);
        PageResult pageResult = new PageResult(carousels, total, pageSize, pageNumber);
        return pageResult;
    }

    @Override
    public int saveCarousel(AdminCarouselParam adminCarouselParam) {
        Carousel carousel = new Carousel();
        carousel.setCarouselRank(adminCarouselParam.getCarsoelRank());
        carousel.setCarouselUrl(adminCarouselParam.getCarouselUrl());
        carousel.setRedirectUrl(adminCarouselParam.getRedirectUrl());
        return carouselMapper.insertSelective(carousel);
    }
}
