package com.geekaca.mall.controller.vo;

import lombok.Data;

@Data
public class FrontSearchPageVO {
    private Integer PageNumber;
    private Integer PageSize;
    private String keyword;
    private Long goodsCategoryId;
    private String orderBy;

    private Integer start;

    public FrontSearchPageVO() {
    }

    public FrontSearchPageVO(Integer pageNumber, Integer pageSize) {
        PageNumber = pageNumber;
        PageSize = pageSize;
        this.start = (pageNumber - 1) * pageSize;//自行计算start
    }
}
