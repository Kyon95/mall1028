package com.geekaca.mall.controller.fore;

import com.geekaca.mall.controller.vo.IndexCarouselAndGoodsVO;
import com.geekaca.mall.controller.vo.IndexHotGoodsInfoVO;
import com.geekaca.mall.controller.vo.IndexNewGoodsInfoVO;
import com.geekaca.mall.controller.vo.IndexRecommendGoodsInfoVO;
import com.geekaca.mall.service.GoodsInfoService;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class IndexConfigController {
    @Autowired
    private GoodsInfoService goodsInfoService;

    /**
     * 顶部商品轮播图
     * 以及下方的
     * 热销商品、新品上线、推荐商品
     */
    @GetMapping("/index-infos")
    public Result showCarouselAndGoods() {
        List<IndexHotGoodsInfoVO> hotGoodses = goodsInfoService.getHotGoods();
        List<IndexNewGoodsInfoVO> newGoodses = goodsInfoService.getNewGoods();
        List<IndexRecommendGoodsInfoVO> recommendGoodses = goodsInfoService.getRecommendGoods();


        IndexCarouselAndGoodsVO data = new IndexCarouselAndGoodsVO();
        data.setHotGoodses(hotGoodses);
        data.setNewGoodses(newGoodses);
        data.setRecommendGoodses(recommendGoodses);

        return ResultGenerator.genSuccessResult(data);
    }

}
