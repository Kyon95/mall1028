package com.geekaca.mall;

import com.geekaca.mall.utils.RedisReader;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableScheduling
@SpringBootApplication
@MapperScan("com.geekaca.mall.mapper") // 扫描 Mapper 接口
public class Mall1028Application {

    @Autowired
    private RedisReader redisReader;

    public static void main(String[] args) {
        SpringApplication.run(Mall1028Application.class, args);
    }

    @Scheduled(fixedRate = 1000 * 60 * 30) // 每半小时刷新
    public void reportCurrentTime() {
        // 定时把前台类别数据读入redis
        log.info("每半小时把前台类别数据读入redis");
        redisReader.cateReader();
    }


}
