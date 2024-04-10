package com.geekaca.mall.utils;

import com.alibaba.fastjson.JSON;
import com.geekaca.mall.service.GoodsCateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Slf4j
public class RedisReader {

    @Autowired
    private GoodsCateService goodsCateService;
    @Autowired
    private JedisPool jedisPool;

    public static void cateReader(JedisPool jedisPool,GoodsCateService goodsCateService){
        Jedis jedis = jedisPool.getResource();
        List<List> allCatoriesAndSubCatories = goodsCateService.findAllCatoriesAndSubCatories();
        log.info("从数据库中获取数据");
        String s = JSON.toJSONString(allCatoriesAndSubCatories);
        jedis.set("allcategories", s);
        jedis.close();
    }
}
