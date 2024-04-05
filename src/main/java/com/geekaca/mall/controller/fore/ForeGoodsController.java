package com.geekaca.mall.controller.fore;

import com.geekaca.mall.controller.vo.FrontSearchPageVO;
import com.geekaca.mall.exceptions.MallException;
import com.geekaca.mall.service.GoodsInfoService;
import com.geekaca.mall.utils.PageResult;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ForeGoodsController {
    @Autowired
    private GoodsInfoService goodsInfoService;

    /**
     * 前台商品搜索
     *
     * @param keyword
     * @param goodsCategoryId
     * @param orderBy
     * @param pageNumber
     * @return
     */
    @GetMapping("/search")
    public Result search(@RequestParam(required = false) String keyword,
                         @RequestParam(required = false) Long goodsCategoryId,
                         @RequestParam(required = false) String orderBy,
                         @RequestParam(required = false) Integer pageNumber) {
        //两个搜索参数都为空，直接返回异常
        if (goodsCategoryId == null && !StringUtils.hasText(keyword)) {
            throw new MallException("非法的搜索参数");
        }
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        int pageSize = 10;
        FrontSearchPageVO frontPageVO = new FrontSearchPageVO(pageNumber, pageSize);
        frontPageVO.setGoodsCategoryId(goodsCategoryId);
        if (StringUtils.hasText(keyword)) {
            frontPageVO.setKeyword(keyword.trim());
        }
        if (StringUtils.hasText(orderBy)) {
            frontPageVO.setOrderBy(orderBy.trim());
        }

        PageResult pageResult = goodsInfoService.searchFrontGoods(frontPageVO);
        return ResultGenerator.genSuccessResult(pageResult);
    }

}
