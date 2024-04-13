package com.geekaca.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.geekaca.mall.controller.vo.GoodsCategoryVO;
import com.geekaca.mall.controller.vo.SecondLevelCategoryVO;
import com.geekaca.mall.controller.vo.ThirdLevelCategoryVO;
import com.geekaca.mall.domain.GoodsCategory;
import com.geekaca.mall.domain.GoodsInfo;
import com.geekaca.mall.exceptions.MallException;
import com.geekaca.mall.mapper.GoodsCategoryMapper;
import com.geekaca.mall.mapper.GoodsInfoMapper;
import com.geekaca.mall.service.GoodsCateService;
import com.geekaca.mall.service.GoodsInfoService;
import com.geekaca.mall.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class GoodsCateServiceImpl implements GoodsCateService {
    @Autowired
    GoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    GoodsInfoService goodsInfoService;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public PageResult findAllGoodsCategory(Integer pageNumber, Integer pageSize, Integer categoryLevel, Integer parentId) {
        Jedis jedis = jedisPool.getResource();
        String allCategoriesBackStr = jedis.get("allCategoriesBack:"+pageNumber+":"+pageSize+":"+categoryLevel+":"+parentId);
        List<GoodsCategoryVO> allCategoriesBack = new ArrayList<>();
        // 如果缓存中有数据，则直接返回
        if (allCategoriesBackStr != null && !"".equals(allCategoriesBackStr)) {
            log.info("从缓存中获取数据");
            allCategoriesBack = JSON.parseObject(allCategoriesBackStr, List.class);
            jedis.close();
        } else {
            log.info("从数据库中获取数据");
            List<GoodsCategory> allCategoriesRaw = goodsCategoryMapper.findAllCategories((pageNumber - 1) * pageSize,
                    pageSize,
                    categoryLevel, parentId);
            for (GoodsCategory goodsCategory : allCategoriesRaw) {
                GoodsCategoryVO goodsCategoryVO = new GoodsCategoryVO();
                BeanUtil.copyProperties(goodsCategory, goodsCategoryVO);
                allCategoriesBack.add(goodsCategoryVO);
            }
            jedis.set("allCategoriesBack:"+pageNumber+":"+pageSize+":"+categoryLevel+":"+parentId, JSON.toJSONString(allCategoriesBack));
            jedis.close();
        }

//        List<GoodsCategory> allCategoriesRaw = goodsCategoryMapper.findAllCategories((pageNumber - 1) * pageSize,
//                pageSize,
//                categoryLevel, parentId);
//
//        List<GoodsCategoryVO> allCategories = new ArrayList<>();
//
//        for (GoodsCategory goodsCategory : allCategoriesRaw) {
//            GoodsCategoryVO goodsCategoryVO = new GoodsCategoryVO();
//            BeanUtil.copyProperties(goodsCategory, goodsCategoryVO);
//            allCategories.add(goodsCategoryVO);
//        }
        Integer cateCount = goodsCategoryMapper.findCategoryCount((pageNumber - 1) * pageSize, pageSize, categoryLevel,
                parentId);
        PageResult pageResult = new PageResult(allCategoriesBack, cateCount, pageSize, pageNumber);
        return pageResult;
    }

    @Override
    public Long findParentId(Long categoryId) {
        return goodsCategoryMapper.findParentId(categoryId);
    }

    @Override
    public GoodsCategory findCatgoryById(Long categoryId) {
        return goodsCategoryMapper.selectByPrimaryKey(categoryId);
    }

    @Override
    public int updateGoodsCategory(GoodsCategory goodsCategory) {
        return goodsCategoryMapper.updateByPrimaryKeySelective(goodsCategory);
    }

    @Override
    public int saveGoodsCategory(GoodsCategory goodsCategory) {
        return goodsCategoryMapper.insertSelective(goodsCategory);
    }

    @Override
    public int deleteGoodsCategory(Long categoryId) {
        return 0;
    }

    @Override
    public int deleteGoodsCategoryByIds(Long[] ids) {
        /*必须先删除下级，才能删除类别
        如果有商品使用类别，则该类别也不应该能被删除*/

        for (Long id : ids) {
            int i = goodsCategoryMapper.selectSubIdByParentId(id);
            if (i > 0) {
                throw new MallException("该类别有子类别，请先删除子类别");
            }

            List<GoodsInfo> goodsList = goodsInfoService.getGoodsListByCategoryId(id);
            if (goodsList.size() > 0) {
                throw new MallException("该类别有商品，请先删除商品");
            }
        }
        return goodsCategoryMapper.deleteByIds(ids);
    }

    @Override
    public List<List> findAllCatoriesAndSubCatories() {
        // 总列表
        List bigList = new ArrayList<>();
        List<GoodsCategory> firstLevelCategories = goodsCategoryMapper.findAllCategories(null, null, 1, null);
        List<GoodsCategoryVO> firstLevelCategoryVOS = BeanUtil.copyToList(firstLevelCategories,
                GoodsCategoryVO.class);
        // 遍历一级分类
        for (GoodsCategoryVO firstLevelCategoryVO : firstLevelCategoryVOS) {
            Long firstLevelId = firstLevelCategoryVO.getCategoryId();
            // 把二级分类放在 二级表
            List<GoodsCategory> catL2 = goodsCategoryMapper.findCatByPID(firstLevelId, 2);
            List<SecondLevelCategoryVO> secondLevelCategoryVOS = BeanUtil.copyToList(catL2, SecondLevelCategoryVO.class);
            for (SecondLevelCategoryVO secondLevelCategory : secondLevelCategoryVOS) {
                List<GoodsCategory> thirdLevelCategories = goodsCategoryMapper.findCatByPID(secondLevelCategory.getCategoryId(), 3);
                List<ThirdLevelCategoryVO> thirdLevelCategoryVOS = BeanUtil.copyToList(thirdLevelCategories, ThirdLevelCategoryVO.class);//
                // 把三级列表存入二级对象
                secondLevelCategory.setThirdLevelCategoryVOS(thirdLevelCategoryVOS);
            }
            // 把二级列表存入一级对象
            firstLevelCategoryVO.setSecondLevelCategoryVOS(secondLevelCategoryVOS);
            // 一级对象存入总表
            bigList.add(firstLevelCategoryVO);
        }
        return bigList;
    }
}
