package com.geekaca.mall.service.impl;

import com.geekaca.mall.controller.admin.param.AdminIndexConfigPageParam;
import com.geekaca.mall.domain.GoodsInfo;
import com.geekaca.mall.domain.IndexConfig;
import com.geekaca.mall.exceptions.MallException;
import com.geekaca.mall.mapper.GoodsInfoMapper;
import com.geekaca.mall.mapper.IndexConfigMapper;
import com.geekaca.mall.service.AdminIndexConfigService;
import com.geekaca.mall.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class AdminIndexConfigServiceImpl implements AdminIndexConfigService {
    @Autowired
    private IndexConfigMapper indexConfigMapper;
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    public PageResult getIndexConfigByConfigType(AdminIndexConfigPageParam configPageParam) {
        int totalCnt = indexConfigMapper.selectConfigCntByConfigType(configPageParam.getConfigType());
        if (totalCnt == 0) {
            return new PageResult(Collections.emptyList(), totalCnt,
                    configPageParam.getPageSize(), configPageParam.getPageNumber());
        }
        List<IndexConfig> configList = indexConfigMapper.selectConfigPageByConfigType(configPageParam);
        return new PageResult(configList, totalCnt, configPageParam.getPageSize(), configPageParam.getPageNumber());
    }

    @Override
    public int saveIndexConfig(IndexConfig indexConfig) {
        int cnt = goodsInfoMapper.selectGoodIsExist(indexConfig.getGoodsId());
        if (cnt != 1) {
            throw new MallException("数据库中没有该商品编号");
        }
        return indexConfigMapper.insertSelective(indexConfig);
    }

    @Override
    public IndexConfig getIndexConfigById(Long configId) {
        return indexConfigMapper.selectByPrimaryKey(configId);
    }

    @Override
    public int editIndexConfig(IndexConfig indexConfig) {
        //补上时间，修改update_time
        indexConfig.setUpdateTime(new Date());
        return indexConfigMapper.updateByPrimaryKeySelective(indexConfig);
    }
}
