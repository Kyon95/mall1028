package com.geekaca.mall.controller.admin;

import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.domain.GoodsInfo;
import com.geekaca.mall.service.GoodsInfoService;
import com.geekaca.mall.utils.JwtUtil;
import com.geekaca.mall.utils.PageResult;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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

    @ApiOperation(value = "添加商品", notes = "添加商品")
    @PostMapping("/goods")
    public Result insertGood(
//            @RequestParam(required = false) @ApiParam(value = "分类ID") Integer goodsCategoryId,
//                             @RequestParam(required = false) @ApiParam(value = "商品主图") String goodsCoverImg,
//                             @RequestParam(required = false) @ApiParam(value = "详情内容") String goodsDetailContent,
//                             @RequestParam(required = false) @ApiParam(value = "商品简介") String goodsIntro,
//                             @RequestParam(required = false) @ApiParam(value = "商品名称") String goodsName,
//                             @RequestParam(required = false) @ApiParam(value = "上架状态 0-上架 1-下架") Integer goodsSellStatus,
//                             @RequestParam(required = false) @ApiParam(value = "进货价") Integer originalPrice,
//                             @RequestParam(required = false) @ApiParam(value = "商品售卖价") Integer sellingPrice,
//                             @RequestParam(required = false) @ApiParam(value = "商品库存") Integer stockNum,
//                             @RequestParam(required = false) @ApiParam(value = "商品标签") String tag
            @RequestBody GoodsInfo goodsInfo, HttpServletRequest request
            ){

//        goodsInfo.setGoodsCarousel("");
        String token = request.getHeader("token");
        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim idClaim = stringClaimMap.get("id");
        String uidStr = idClaim.asString();
        Integer uid = Integer.valueOf(uidStr);
        Claim userNameClaim = stringClaimMap.get("userName");
        String userName = userNameClaim.asString();
        int i = goodsInfoService.insertGood(goodsInfo);

        goodsInfo.setCreateUser(uid);

        if(i>0){
            return ResultGenerator.genSuccessResult("添加商品成功");
        }else{
            return ResultGenerator.genFailResult("添加失败");
        }


    }
}
