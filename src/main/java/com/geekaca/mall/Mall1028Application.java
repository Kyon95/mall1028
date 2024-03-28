package com.geekaca.mall;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
@MapperScan("com.geekaca.mall.mapper") // 扫描 Mapper 接口
public class Mall1028Application {

    public static void main(String[] args) {
        log.info("test 1028");
        SpringApplication.run(Mall1028Application.class, args);
    }

}
