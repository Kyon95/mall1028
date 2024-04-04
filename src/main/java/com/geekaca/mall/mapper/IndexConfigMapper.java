package com.geekaca.mall.mapper;

import com.geekaca.mall.controller.admin.param.AdminIndexConfigPageParam;
import com.geekaca.mall.domain.IndexConfig;

import java.util.List;

/**
* @author katagiri
* @description 针对表【tb_newbee_mall_index_config(配置表，保存针对首页配置信息)】的数据库操作Mapper
* @createDate 2024-04-04 14:18:09
* @Entity com.geekaca.mall.domain.IndexConfig
*/
public interface IndexConfigMapper {

    int deleteByPrimaryKey(Long id);

    int insert(IndexConfig record);

    /**
     * 新增配置
     */
    int insertSelective(IndexConfig record);

    /**
     * 某配置详情
     */
    IndexConfig selectByPrimaryKey(Long id);

    /**
     * 修改某配置信息
     */
    int updateByPrimaryKeySelective(IndexConfig record);

    int updateByPrimaryKey(IndexConfig record);

    /**
     * 凭configType  获取该configType下有多少条数据
     */
    int selectConfigCntByConfigType(Integer configType);

    /**
     * 凭configType  获取该configType下的所有数据
     */
    List<IndexConfig> selectConfigPageByConfigType(AdminIndexConfigPageParam configPageParam);
}
