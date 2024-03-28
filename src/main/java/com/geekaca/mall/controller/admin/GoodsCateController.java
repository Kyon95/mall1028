package com.geekaca.mall.controller.admin;

import com.geekaca.mall.service.GoodsCateService;
import com.geekaca.mall.utils.PageResult;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping({"/manage-api/v1"})
public class GoodsCateController {
    @Autowired
    GoodsCateService goodsCateService;

    @GetMapping("/categories")
    public Result allCategories(@RequestParam(required = false) @ApiParam Integer pageNumber,
                                @RequestParam(required = false) @ApiParam Integer pageSize,
                                @RequestParam(required = false) @ApiParam Integer categoryLevel,
                                @RequestParam(required = false) @ApiParam Integer parentId) {

        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (pageSize == null) {
            pageSize = 1000;
        }
        if (categoryLevel == null) {
            categoryLevel = 1;
        }
        if (parentId == null) {
            parentId = 0;
        }
        PageResult allGoodsCategory = goodsCateService.findAllGoodsCategory(pageNumber, pageSize, categoryLevel, parentId);
        Result result = ResultGenerator.genSuccessResult();
        result.setData(allGoodsCategory);
        return result;
    }
}
