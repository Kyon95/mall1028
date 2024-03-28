package com.geekaca.mall.controller.admin;

import com.geekaca.mall.domain.GoodsCategory;
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
    private GoodsCateService goodsCateService;

    @GetMapping("/categories")
    public Result allCategories(@RequestParam(required = false) @ApiParam Integer pageNumber,
                                @RequestParam(required = false) @ApiParam Integer pageSize,
                                @RequestParam(required = false) @ApiParam Integer categoryLevel,
                                @RequestParam(required = false) @ApiParam Integer parentId) {

        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (pageSize == null) {
            pageSize = 5;
        }
        if (categoryLevel == null) {
            categoryLevel = 1;
        }
        if (parentId == null) {
            parentId = 0;
        }
        
        PageResult pageRs = goodsCateService.findAllGoodsCategory(pageNumber, pageSize, categoryLevel, parentId);
        Result result = ResultGenerator.genSuccessResult();
        result.setData(pageRs);
        return result;
    }

    @GetMapping("/categories/{id}")
    public Result getCategoryById(@PathVariable("id") Integer id){
        GoodsCategory category = new GoodsCategory();
        //todo: 根据id 查询类别信息
        return ResultGenerator.genSuccessResult(category);
    }
}
