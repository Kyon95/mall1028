package com.geekaca.mall.controller.fore.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MallUserUpdateParam implements Serializable {
    @ApiModelProperty("数据库ID")
    private Long userId;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("用户密码(需要MD5加密)")
    private String passwordMd5;
    @ApiModelProperty("个性签名")
    private String introduceSign;
}
