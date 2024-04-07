package com.geekaca.mall;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.geekaca.mall.domain.MallUser;
import com.geekaca.mall.mapper.MallUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Mall1028ApplicationTests {
    @Autowired
    private MallUserMapper userMapper;

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
