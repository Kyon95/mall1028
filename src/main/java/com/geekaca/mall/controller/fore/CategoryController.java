package com.geekaca.mall.controller.fore;

import com.alibaba.fastjson.JSON;
import com.geekaca.mall.exceptions.MallException;
import com.geekaca.mall.service.GoodsCateService;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")

public class CategoryController {
    @Autowired
    private GoodsCateService goodsCateService;
    @Autowired
    private JedisPool jedisPool;

    @GetMapping("/categories")
    public Result getCategories() {
        log.info("获取分类数据");
//         从redis 读取 categories，提高访问速度
        Jedis jedis = jedisPool.getResource();
        String allcategoriesStr = jedis.get("allcategories");

        if(allcategoriesStr != null){
            List<List> allcategories = JSON.parseObject(allcategoriesStr, List.class);
            log.info("从缓存中获取数据");
            jedis.close();
            return ResultGenerator.genSuccessResult(allcategories);
        }
        else {
            throw new MallException("缓存读取失败");
        }
    }
}


