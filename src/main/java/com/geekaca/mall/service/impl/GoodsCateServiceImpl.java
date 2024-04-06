package com.geekaca.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.geekaca.mall.controller.vo.ThirdLevelCategoryVO;
import com.geekaca.mall.domain.GoodsCategory;
import com.geekaca.mall.exceptions.MallException;
import com.geekaca.mall.mapper.GoodsCategoryMapper;
import com.geekaca.mall.service.GoodsCateService;
import com.geekaca.mall.utils.PageResult;
import com.geekaca.mall.controller.vo.SecondLevelCategoryVO;
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

        List<GoodsCategory> allCategories = goodsCategoryMapper.findAllCategories((pageNumber - 1) * pageSize, pageSize, categoryLevel, parentId);
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
    public List<Object> findAllCatoriesAndSubCatories() {
        // 总列表
        List<Object> bigList = new ArrayList<>();
        List<GoodsCategory> firstLevelCategories = goodsCategoryMapper.findAllCategories(null, null, 1, null);
        // 遍历一级分类
        for (GoodsCategory firstLevelCategory : firstLevelCategories) {
            Long firstLevelId = firstLevelCategory.getCategoryId();
            // 把二级分类放在 二级表
            List<GoodsCategory> catL2 = goodsCategoryMapper.findCatByPID(firstLevelId, 2);
            List<SecondLevelCategoryVO> newCatL2 = new ArrayList<>();
            for (GoodsCategory cat : catL2) {
                System.out.println(cat);
                SecondLevelCategoryVO secondLevelCategory = new SecondLevelCategoryVO();

                secondLevelCategory.setCategoryId(cat.getCategoryId());
                secondLevelCategory.setCategoryLevel(cat.getCategoryLevel());
                secondLevelCategory.setCategoryName(cat.getCategoryName());
                secondLevelCategory.setCategoryRank(cat.getCategoryRank());
                secondLevelCategory.setParentId(cat.getParentId());
                // 查找三级对象列表
                List<GoodsCategory> L3List = goodsCategoryMapper.findCatByPID(cat.getCategoryId(), 3);
                List<ThirdLevelCategoryVO> thirdLevelCategoryVOS = new ArrayList<>();
                for (GoodsCategory goodsCategory : L3List) {
                    ThirdLevelCategoryVO thirdLevelCategoryVO = new ThirdLevelCategoryVO();
                    BeanUtil.copyProperties(goodsCategory, thirdLevelCategoryVO);
                    thirdLevelCategoryVOS.add(thirdLevelCategoryVO);
                }
                // 把三级列表存入二级对象
                secondLevelCategory.setThirdLevelCategoryVOS(thirdLevelCategoryVOS);
//                secondLevelCategory.setThirdLevelCategoryVOS(L3List);
                // 二级列表加添加二级对象
                newCatL2.add(secondLevelCategory);
            }
            // 把二级列表存入一级对象
            firstLevelCategory.setSecondLevelCategoryVOS(newCatL2);
            // 一级对象存入总表
            bigList.add(firstLevelCategory);
        }
        return bigList;
    }
}
