package com.geekaca.mall;

import com.geekaca.mall.service.GoodsCateService;
import com.geekaca.mall.utils.RedisReader;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;

@Slf4j
@EnableScheduling
@SpringBootApplication
@MapperScan("com.geekaca.mall.mapper") // 扫描 Mapper 接口
public class Mall1028Application {
    @Autowired
    private GoodsCateService goodsCateService;
    @Autowired
    private JedisPool jedisPool;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        log.info("test 1028");
        SpringApplication.run(Mall1028Application.class, args);
    }

    @Scheduled(fixedRate = 1000 * 60 * 30) // 每半小时刷新
    public void reportCurrentTime() {
        //参考：https://www.cnblogs.com/coderacademy/p/18058208
        //    System.out.println("每隔五秒执行一次" + dateFormat.format(new Date()));
        // 定时把前台类别数据读入redis
        log.info("每半小时把前台类别数据读入redis");
//        Jedis jedis = jedisPool.getResource();
//        List<List> allCatoriesAndSubCatories = goodsCateService.findAllCatoriesAndSubCatories();
//        log.info("从数据库中获取数据");
//        String s = JSON.toJSONString(allCatoriesAndSubCatories);
//        jedis.set("allcategories", s);
//        jedis.close();
        RedisReader.cateReader(jedisPool,goodsCateService);
    }

    /**
     * 每五秒执行一次
     * <p>
     * https://juejin.cn/post/7013234573823705102#heading-1
     * 注解方式
     */
//    @Scheduled(cron = "*/5 * * * * ?")
//    private void printNowDate() {
//        long nowDateTime = System.currentTimeMillis();
//        System.out.println("固定定时任务执行:--->" + nowDateTime + "，此任务为每五秒执行一次");
//    }

}
