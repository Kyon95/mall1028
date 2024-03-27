package com.geekaca.mall.controller.fore.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class MallUserLoginParam implements Serializable {

    @NotEmpty(message = "登录名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String passwordMd5;
}
