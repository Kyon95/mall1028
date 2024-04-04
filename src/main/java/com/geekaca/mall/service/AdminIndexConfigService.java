package com.geekaca.mall.service;

import com.geekaca.mall.controller.admin.param.AdminIndexConfigPageParam;
import com.geekaca.mall.domain.IndexConfig;
import com.geekaca.mall.utils.PageResult;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AdminIndexConfigService {

    PageResult getIndexConfigByConfigType(AdminIndexConfigPageParam indexConfigPageParam);

    int saveIndexConfig(IndexConfig indexConfig);

    IndexConfig getIndexConfigById(Long configId);

    int editIndexConfig(IndexConfig indexConfig);
}
