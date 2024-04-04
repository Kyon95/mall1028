package com.geekaca.mall.controller.admin.param;

import lombok.Data;

@Data
public class AdminIndexConfigPageParam {
    private Integer pageNumber;
    private Integer pageSize;
    private Integer configType;
    private Integer start;

    public AdminIndexConfigPageParam() {
    }

    public AdminIndexConfigPageParam(Integer pageNumber, Integer pageSize, Integer configType) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.configType = configType;
        this.start = (pageNumber - 1) * pageSize;//自行计算start
    }
}
