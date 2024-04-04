package com.geekaca.mall.service.impl;

import com.geekaca.mall.controller.admin.param.AdminIndexConfigPageParam;
import com.geekaca.mall.domain.IndexConfig;
import com.geekaca.mall.mapper.IndexConfigMapper;
import com.geekaca.mall.service.AdminIndexConfigService;
import com.geekaca.mall.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AdminIndexConfigServiceImpl implements AdminIndexConfigService {
    @Autowired
    private IndexConfigMapper indexConfigMapper;

    @Override
    public PageResult getIndexConfigByConfigType(AdminIndexConfigPageParam configPageParam) {
        int totalCnt = indexConfigMapper.selectConfigCntByConfigType(configPageParam.getConfigType());
        if(totalCnt == 0){
            return new PageResult(Collections.emptyList(), totalCnt,
                    configPageParam.getPageSize(), configPageParam.getPageNumber());
        }
        List<IndexConfig> configList = indexConfigMapper.selectConfigPageByConfigType(configPageParam);
        return new PageResult(configList, totalCnt, configPageParam.getPageSize(), configPageParam.getPageNumber());
    }

    @Override
    public int saveIndexConfig(IndexConfig indexConfig) {
        return indexConfigMapper.insertSelective(indexConfig);
    }

    @Override
    public IndexConfig getIndexConfigById(Long configId) {
        return indexConfigMapper.selectByPrimaryKey(configId);
    }
}
