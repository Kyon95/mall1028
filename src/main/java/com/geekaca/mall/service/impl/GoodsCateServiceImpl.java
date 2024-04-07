package com.geekaca.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.geekaca.mall.controller.vo.GoodsCategoryVO;
import com.geekaca.mall.controller.vo.SecondLevelCategoryVO;
import com.geekaca.mall.controller.vo.ThirdLevelCategoryVO;
import com.geekaca.mall.domain.GoodsCategory;
import com.geekaca.mall.exceptions.MallException;
import com.geekaca.mall.mapper.GoodsCategoryMapper;
import com.geekaca.mall.service.GoodsCateService;
import com.geekaca.mall.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsCateServiceImpl implements GoodsCateService {
    @Autowired
    GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public PageResult findAllGoodsCategory(Integer pageNumber, Integer pageSize, Integer categoryLevel, Integer parentId) {

        List<GoodsCategory> allCategoriesRaw = goodsCategoryMapper.findAllCategories((pageNumber - 1) * pageSize,
                pageSize,
                categoryLevel, parentId);

        List<GoodsCategoryVO> allCategories = new ArrayList<>();

        for (GoodsCategory goodsCategory : allCategoriesRaw) {
            GoodsCategoryVO goodsCategoryVO = new GoodsCategoryVO();
            BeanUtil.copyProperties(goodsCategory, goodsCategoryVO);
            allCategories.add(goodsCategoryVO);
        }


        Integer cateCount = goodsCategoryMapper.findCategoryCount((pageNumber - 1) * pageSize, pageSize, categoryLevel,
                parentId);
        PageResult pageResult = new PageResult(allCategories, cateCount, pageSize, pageNumber);
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
    public int deleteGoodsCategoryByIds(List<Integer> ids) {
        //必须先删除下级，才能删除类别
        for (Integer id : ids) {
            int i = goodsCategoryMapper.selectSubIdByParentId(id);
            if (i > 0) {
                throw new MallException("该类别有子类别，请先删除子类别");
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
