package com.geekaca.mall.controller.admin;

import com.geekaca.mall.controller.admin.param.AdminCarouselParam;
import com.geekaca.mall.controller.admin.param.BatchIdParam;
import com.geekaca.mall.service.AdminCarouselService;
import com.geekaca.mall.utils.PageResult;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/manage-api/v1")
public class AdminCarouselController {
    @Autowired
    private AdminCarouselService adminCarouselService;

    @GetMapping("/carousels")
    public Result findAllCarousels(@RequestParam(required = false) Integer pageNumber,
                                   @RequestParam(required = false) Integer pageSize) {

        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PageResult carouselsPG = adminCarouselService.getCarousels(pageNumber, pageSize);

        if (carouselsPG != null) {
            return ResultGenerator.genSuccessResult(carouselsPG);
        } else {
            return ResultGenerator.genFailResult("查询失败");
        }
    }

    @PostMapping("carousels")
    public Result saveCarousel(@RequestBody AdminCarouselParam adminCarouselParam) {

        if (adminCarouselService.saveCarousel(adminCarouselParam)>0) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("保存失败");
        }
    }

    @DeleteMapping("carousels")
    public Result deleteCarousel(@RequestBody BatchIdParam batchIdParam) {

        if (adminCarouselService.deleteCarousel(batchIdParam)>0) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }
}
