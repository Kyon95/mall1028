package com.geekaca.mall.controller.admin;

import com.geekaca.mall.service.GoodsInfoService;
import com.geekaca.mall.utils.PageResult;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/manage-api/v1")
public class GoodsController {
    @Autowired
    private GoodsInfoService goodsInfoService;

    @ApiOperation(value = "商品列", notes = "查询所有商品")
    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    public Result list(@RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
                       @RequestParam(required = false) @ApiParam(value = "每页条数") Integer pageSize,
                       @RequestParam(required = false) @ApiParam(value = "商品名称") String goodsName,
                       @RequestParam(required = false) @ApiParam(value = "上架状态 0-上架 1-下架") Integer goodsSellStatus) {

        if(pageNumber==null){
            pageNumber=1;
        }
        if(pageSize==null){
            pageSize=20;
        }
        PageResult pageResult = goodsInfoService.findAllGoods(pageNumber, pageSize,goodsName);

        Result result = ResultGenerator.genSuccessResult();
        result.setData(pageResult);
        return result;
    }
}
