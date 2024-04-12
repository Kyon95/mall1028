package com.geekaca.mall.mapper;

import com.geekaca.mall.domain.Carousel;
import com.geekaca.mall.utils.PageResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Peter
* @description 针对表【tb_newbee_mall_carousel(前台首页轮播图)】的数据库操作Mapper
* @createDate 2024-04-11 23:42:10
* @Entity com.geekaca.mall.domain.Carousel
*/
@Mapper
public interface CarouselMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Carousel record);

    int insertSelective(Carousel record);

    Carousel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Carousel record);

    int updateByPrimaryKey(Carousel record);

    List<Carousel> getAllCarousels(@Param("limit") Integer limit,@Param("pageSize") Integer pageSize);

    Integer getTotalCarousels();

    int deleteByPrimaryKeys(@Param("ids")Long[] ids);
}
