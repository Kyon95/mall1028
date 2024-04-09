package com.geekaca.mall;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@EnableScheduling
@SpringBootApplication
@MapperScan("com.geekaca.mall.mapper") // 扫描 Mapper 接口
public class Mall1028Application {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void main(String[] args) {
        log.info("test 1028");
        SpringApplication.run(Mall1028Application.class, args);
    }
    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        //参考：https://www.cnblogs.com/coderacademy/p/18058208
        System.out.println("每隔五秒执行一次" + dateFormat.format(new Date()));
    }

    /**
     * 每五秒执行一次
     *
     * https://juejin.cn/post/7013234573823705102#heading-1
     * 注解方式
     */
    @Scheduled(cron = "*/5 * * * * ?")
    private void printNowDate() {
        long nowDateTime = System.currentTimeMillis();
        System.out.println("固定定时任务执行:--->"+nowDateTime+"，此任务为每五秒执行一次");
    }

}
