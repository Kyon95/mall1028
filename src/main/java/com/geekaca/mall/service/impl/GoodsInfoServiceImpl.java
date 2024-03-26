package com.geekaca.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.geekaca.mall.controller.vo.IndexHotGoodsInfoVO;
import com.geekaca.mall.controller.vo.IndexNewGoodsInfoVO;
import com.geekaca.mall.controller.vo.IndexRecommendGoodsInfoVO;
import com.geekaca.mall.mapper.GoodsInfoMapper;
import com.geekaca.mall.service.GoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
