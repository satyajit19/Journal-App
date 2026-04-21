package com.Test.demo.Test;

import com.Test.demo.services.SendMailService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class redisTest {

    @Autowired
    private RedisTemplate redisTemplate;
    @Disabled
    @Test
    public void redisTest(){
        redisTemplate.opsForValue().set("email","hi");
        redisTemplate.opsForValue().get("email");

    }
}
