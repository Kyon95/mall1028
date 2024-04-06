package com.geekaca.mall.controller.fore;

import com.geekaca.mall.service.GoodsCateService;
import com.geekaca.mall.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")

public class CategoryController {
    @Autowired
    private GoodsCateService goodsCateService;

    @GetMapping("/categories")
    public Result getCategories() {
        List<List> allCatoriesAndSubCatories = goodsCateService.findAllCatoriesAndSubCatories();
        System.out.println("hello");
        Result result = new Result();
        result.setData(allCatoriesAndSubCatories);
        result.setResultCode(200);
        result.setMessage("success");
        return result;
    }
}


