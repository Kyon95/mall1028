package com.geekaca.mall.controller.admin;

import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.domain.GoodsCategory;
import com.geekaca.mall.domain.GoodsInfo;
import com.geekaca.mall.service.GoodsCateService;
import com.geekaca.mall.service.GoodsInfoService;
import com.geekaca.mall.utils.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/manage-api/v1")
public class GoodsController {
    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private GoodsCateService goodsCateService;

    @ApiOperation(value = "商品列", notes = "查询所有商品")
    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    public Result list(@RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
                       @RequestParam(required = false) @ApiParam(value = "每页条数") Integer pageSize,
                       @RequestParam(required = false) @ApiParam(value = "商品名称") String goodsName,
                       @RequestParam(required = false) @ApiParam(value = "上架状态 0-上架 1-下架") Integer goodsSellStatus) {

        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        PageResult pageResult = goodsInfoService.findAllGoods(pageNumber, pageSize, goodsName);

        Result result = ResultGenerator.genSuccessResult();
        result.setData(pageResult);
        return result;
    }
    //todo:  不要一个方法既能做新增，又能做修改，看似节省了方法定义，但是逻辑不够清晰，不利于测试
    // 开发规范追求的原则之一： 一个方法只做一件事 ，参考《代码简洁之道》 《Clean Code》
    // 而且这种写法在swagger文档里比较混乱
    @ApiOperation(value = "添加/修改商品", notes = "添加/修改商品")
    @RequestMapping(value = "/goods", method = {RequestMethod.POST, RequestMethod.PUT})
    public Result insertGood(
/*            @RequestParam(required = false) @ApiParam(value = "分类ID") Integer goodsCategoryId,
                             @RequestParam(required = false) @ApiParam(value = "商品主图") String goodsCoverImg,
                             @RequestParam(required = false) @ApiParam(value = "详情内容") String goodsDetailContent,
                             @RequestParam(required = false) @ApiParam(value = "商品简介") String goodsIntro,
                             @RequestParam(required = false) @ApiParam(value = "商品名称") String goodsName,
                             @RequestParam(required = false) @ApiParam(value = "上架状态 0-上架 1-下架") Integer goodsSellStatus,
                             @RequestParam(required = false) @ApiParam(value = "进货价") Integer originalPrice,
                             @RequestParam(required = false) @ApiParam(value = "商品售卖价") Integer sellingPrice,
                             @RequestParam(required = false) @ApiParam(value = "商品库存") Integer stockNum,
                             @RequestParam(required = false) @ApiParam(value = "商品标签") String tag*/
            @RequestBody GoodsInfo goodsInfo, HttpServletRequest request
    ) {
        if ("POST".equals(request.getMethod())) {
            // 添加商品
            String token = request.getHeader("token");
            Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
            Claim idClaim = stringClaimMap.get("id");
            String uidStr = idClaim.asString();
            Integer uid = Integer.valueOf(uidStr);
//            Claim userNameClaim = stringClaimMap.get("userName");
//            String userName = userNameClaim.asString();
            int i = goodsInfoService.insertGood(goodsInfo);

            goodsInfo.setCreateUser(uid);

            if (i > 0) {
                return ResultGenerator.genSuccessResult("添加商品成功");
            } else {
                return ResultGenerator.genFailResult("添加失败");
            }

        } else {
            // 修改商品
            int i = goodsInfoService.updateGood(goodsInfo);

            if (i > 0) {
                return ResultGenerator.genSuccessResult("修改商品成功");
            } else {
                return ResultGenerator.genFailResult("修改失败");
            }
        }


    }

    // 根据id查询端口
    @GetMapping("/goods/{id}")
    public Result findGoodById(@PathVariable("id") Long id) {
        // 查找商品信息
        GoodsInfo goodsInfo = goodsInfoService.findGoodById(id);
        // 通过商品名找到类别id
        Long thirdCatId = goodsInfo.getGoodsCategoryId();
        GoodsCategory thirdCategory = goodsCateService.findCatgoryById(thirdCatId);

        // 通过类别id找到父id
        Long secondCatId = goodsCateService.findParentId(thirdCatId);
        GoodsCategory secondCategory = goodsCateService.findCatgoryById(secondCatId);

        // 通过父id找到根id
        Long firstCatId = goodsCateService.findParentId(secondCatId);
        GoodsCategory firstCategory = goodsCateService.findCatgoryById(firstCatId);

        GoodsResult goodsResult = new GoodsResult(goodsInfo, firstCategory, secondCategory, thirdCategory);


        if (goodsResult != null) {
            Result result = ResultGenerator.genSuccessResult("查询商品成功");
            result.setData(goodsResult);
            return result;
        } else {
            return ResultGenerator.genFailResult("查询不到该商品");
        }
    }

    @PutMapping("/goods/status/{status}")
    public Result changeGoodStatus(@PathVariable("status") Integer status,
                                   @RequestBody Map<String, List<Integer>> idMap) {
        int i = goodsInfoService.setGoodStatus(status, idMap.get("ids"));
        if (i > 0) {
            Result result = ResultGenerator.genSuccessResult("更新状态成功");
            return result;
        } else {
            Result result = ResultGenerator.genFailResult("更新状态失败");
            return result;
        }

    }
}
