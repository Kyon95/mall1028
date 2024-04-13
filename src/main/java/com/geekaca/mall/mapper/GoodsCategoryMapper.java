package com.geekaca.mall.mapper;

import com.geekaca.mall.controller.vo.GoodsCategoryVO;
import com.geekaca.mall.domain.GoodsCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Peter
 * @description 针对表【tb_newbee_mall_goods_category】的数据库操作Mapper
 * @createDate 2024-03-27 21:41:54
 * @Entity com.geekaca.mall.domain.GoodsCategory
 */
@Mapper
public interface GoodsCategoryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(GoodsCategory record);

    int insertSelective(GoodsCategory record);

    GoodsCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsCategory record);

    int updateByPrimaryKey(GoodsCategory record);

    List<GoodsCategory> findAllCategories(@Param("limit") Integer limit, @Param("pageSize") Integer pageSize,
                                            @Param("categoryLevel") Integer categoryLevel,
                                            @Param("parentId") Integer parentId);

    int findCategoryCount(@Param("limit") Integer limit, @Param("pageSize") Integer pageSize,
                          @Param("categoryLevel") Integer categoryLevel,
                          @Param("parentId") Integer parentId);

    Long findParentId(@Param("categoryId") Long categoryId);

    List<GoodsCategory> findCatByPID(@Param("parent_id") Long firstLevelId, @Param("category_level") Integer categoryLevel);


    int deleteByIds(@Param("ids") Long[] ids);

    int selectSubIdByParentId(@Param("parentId") Long id);
}
