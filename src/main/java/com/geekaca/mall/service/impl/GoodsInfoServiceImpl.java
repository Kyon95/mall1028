package com.geekaca.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.geekaca.mall.controller.vo.GoodsDetailVO;
import com.geekaca.mall.controller.vo.IndexHotGoodsInfoVO;
import com.geekaca.mall.controller.vo.IndexNewGoodsInfoVO;
import com.geekaca.mall.controller.vo.IndexRecommendGoodsInfoVO;
import com.geekaca.mall.domain.GoodsInfo;
import com.geekaca.mall.mapper.GoodsInfoMapper;
import com.geekaca.mall.service.GoodsInfoService;
import com.geekaca.mall.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.geekaca.mall.common.Constants.*;

@Service
public class GoodsInfoServiceImpl implements GoodsInfoService {
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Override

    public List<IndexHotGoodsInfoVO> getHotGoods() {
        List<IndexHotGoodsInfoVO> hotGoodsVO = goodsInfoMapper
                .selectHotNewCommendGoods(CONFIG_TYPE_HOT, INDEX_G00DS_HOT_NUMBER);
        return hotGoodsVO;
    }

    @Override
    public List<IndexNewGoodsInfoVO> getNewGoods() {
        //IndexHot、New、RecommendGoodsInfoVO三者实体类成员属性相同，且sql语句可重复利用
        //故Mapper.xml中只写了对IndexHot的映射<resultMap
        //可以首先得到Hot  再将Hot转为New和Recommend
        List<IndexHotGoodsInfoVO> hotGoodsVO = goodsInfoMapper
                .selectHotNewCommendGoods(CONFIG_TYPE_NEW, INDEX_GOODS_NEW_NUMBER);
        List<IndexNewGoodsInfoVO> newGoodsVO = BeanUtil
                .copyToList(hotGoodsVO, IndexNewGoodsInfoVO.class);
        return newGoodsVO;
    }

    @Override
    public List<IndexRecommendGoodsInfoVO> getRecommendGoods() {
        List<IndexHotGoodsInfoVO> hotGoodsVO = goodsInfoMapper
                .selectHotNewCommendGoods(CONFIG_TYPE_RECOMMEND, INDEX_GOODS_RECOMMOND_NUMBER);
        List<IndexRecommendGoodsInfoVO> recommendGoodsVO = BeanUtil
                .copyToList(hotGoodsVO, IndexRecommendGoodsInfoVO.class);
        return recommendGoodsVO;
    }
    public PageResult findAllGoods(Integer pageNo, Integer pageSize,String goodsName) {
        Integer limit = (pageNo - 1) * pageSize;
        List<GoodsInfo> goodsList = goodsInfoMapper.findGoodsList(limit, pageSize,goodsName);
        int goodsCount = goodsInfoMapper.findGoodsCount(goodsName);
        PageResult pageResult = new PageResult(goodsList, goodsCount, pageSize, pageNo);
        return pageResult;
    }

    @Override
    public GoodsDetailVO getById(Long goodsId) {
        GoodsInfo goodsInfo = goodsInfoMapper.selectDetailById(goodsId);
        if (goodsInfo == null) {
            return null;
        }
        //前端页面要求：
        //"resultCode": 200,
        //"message": "SUCCESS",
        //"data": {
        //    "goodsCarouselList": ["https://.../images/p40-silver.png"],
        //    "goodsCoverImg": "https://.../images/p40-silver.png",
        //    "goodsDetailContent": "https://.../images/p40-detail.jpg"
        //    "goodsId"
        //    ........
        //}
        //要把goods_carousel字段信息放进集合list
        String goodsCarousel = goodsInfo.getGoodsCarousel();
        List list = new ArrayList();
        list.add(goodsCarousel);
        //其余字段信息，利用工具赋值给VO
        GoodsDetailVO VO = BeanUtil.copyProperties(goodsInfo,GoodsDetailVO.class);
        //最后把list放进VO
        VO.setGoodsCarouselList(list);
        return VO;
    }

    @Override
    public int insertGood(GoodsInfo goodsInfo) {
        int inserted = goodsInfoMapper.insertSelective(goodsInfo);
        return inserted;
    }
}
