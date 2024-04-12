package com.geekaca.mall;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.geekaca.mall.domain.Carousel;
import com.geekaca.mall.domain.MallUser;
import com.geekaca.mall.mapper.CarouselMapper;
import com.geekaca.mall.mapper.MallUserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
class Mall1028ApplicationTests {
    @Autowired
    private MallUserMapper userMapper;
    @Autowired
    private CarouselMapper carouselMapper;

    @Test
    void testCaousels() {
        // 查询轮播图
        List<Carousel> allCarousels = carouselMapper.getAllCarousels(0, 10);

        allCarousels.forEach(System.out::println);
        Assertions.assertTrue(allCarousels.size() > 0);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testLogin() {
        MallUser mallUser = userMapper.checkLogin("13711113333", "e10adc3949ba59abbe56e057f20f883e");
        System.out.println(mallUser.getUserId());
    }

    @Test
    void testSnow() {
        Snowflake snowflake = IdUtil.getSnowflake(1);
        long l = snowflake.nextId();
        System.out.println("myId: " + l);
    }

}
