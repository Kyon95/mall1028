package com.geekaca.mall.mapper;

import com.geekaca.mall.domain.MallCarousel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Kyon_Yjx
* @description 针对表【tb_newbee_mall_carousel(前台首页轮播图)】的数据库操作Mapper
* @createDate 2024-03-28 22:27:36
* @Entity com.geekaca.mall.domain.MallCarousel
*/
@Mapper
public interface MallCarouselMapper {

    int deleteByPrimaryKey(Long id);

    int insert(MallCarousel record);

    int insertSelective(MallCarousel record);

    MallCarousel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MallCarousel record);

    int updateByPrimaryKey(MallCarousel record);

    List<MallCarousel> selectAll(int num);

}
