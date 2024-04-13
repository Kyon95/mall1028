package com.geekaca.mall.controller.admin;

import com.geekaca.mall.controller.BatchIdParam;
import com.geekaca.mall.domain.GoodsCategory;
import com.geekaca.mall.service.GoodsCateService;
import com.geekaca.mall.utils.PageResult;
import com.geekaca.mall.utils.RedisReader;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping({"/manage-api/v1"})
public class GoodsCateController {
    @Autowired
    private GoodsCateService goodsCateService;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private RedisReader redisReader;

    @GetMapping("/categories")
    public Result allCategories(@RequestParam(required = false) @ApiParam Integer pageNumber,
                                @RequestParam(required = false) @ApiParam Integer pageSize,
                                @RequestParam(required = false) @ApiParam Integer categoryLevel,
                                @RequestParam(required = false) @ApiParam Integer parentId) {

        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (pageSize == null) {
            pageSize = 5;
        }
        if (categoryLevel == null) {
            categoryLevel = 1;
        }
        if (parentId == null) {
            parentId = 0;
        }
        pageSize = 5;

        PageResult pageRs = goodsCateService.findAllGoodsCategory(pageNumber, pageSize, categoryLevel, parentId);
        Result result = ResultGenerator.genSuccessResult();
        result.setData(pageRs);
        return result;
    }

    @PostMapping("/categories")
    public Result addCategory(@RequestBody GoodsCategory goodsCategory) {

        int i = goodsCateService.saveGoodsCategory(goodsCategory);
        if (i > 0) {
            // 添加成功后，删除缓存中的分类信息
            try (Jedis jedis = jedisPool.getResource();) {
                Set<String> keys = jedis.keys("allCategoriesBack:*");
                for (String key : keys) {
                    log.info("key: " + key);
                    jedis.del(key);
                }
            }
            // 更新redis中的类别信息
            redisReader.cateReader();
            return ResultGenerator.genSuccessResult("添加成功");
        } else {
            return ResultGenerator.genFailResult("添加失败");
        }
    }

    @DeleteMapping("/categories")
    public Result deleteCategory(@RequestBody BatchIdParam batchIdParam) {
        int i = goodsCateService.deleteGoodsCategoryByIds(batchIdParam.getIds());
        if (i > 0) {
            // 更新成功后，删除缓存中的分类信息
            try (Jedis jedis = jedisPool.getResource();) {
                Set<String> keys = jedis.keys("allCategoriesBack:*");
                for (String key : keys) {
                    log.info("key: " + key);
                    jedis.del(key);
                }
            }
            // 更新redis中的类别信息
            redisReader.cateReader();
            return ResultGenerator.genSuccessResult("删除成功");
        }
        return ResultGenerator.genFailResult("删除失败");
    }

    @GetMapping("/categories/{id}")
    public Result getCategoryById(@PathVariable("id") Long id) {
        GoodsCategory goodsCategory = goodsCateService.findCatgoryById(id);
        return ResultGenerator.genSuccessResult(goodsCategory);
    }

    @PutMapping("/categories")
    public Result updateCategoryById(@RequestBody GoodsCategory goodsCategory) {
        int i = goodsCateService.updateGoodsCategory(goodsCategory);
        if (i > 0) {
            // 更新成功后，删除缓存中的分类信息
            try (Jedis jedis = jedisPool.getResource();) {
                Set<String> keys = jedis.keys("allCategoriesBack:*");
                for (String key : keys) {
                    log.info("key: " + key);
                    jedis.del(key);
                }
            }
            // 更新redis中的类别信息
            redisReader.cateReader();
            return ResultGenerator.genSuccessResult("修改成功");
        } else {
            return ResultGenerator.genFailResult("修改失败");
        }

    }
}
