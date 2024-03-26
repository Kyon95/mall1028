package com.geekaca.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.geekaca.mall.mapper") // 扫描 Mapper 接口
public class Mall1028Application {

    public static void main(String[] args) {
        SpringApplication.run(Mall1028Application.class, args);
    }

}
