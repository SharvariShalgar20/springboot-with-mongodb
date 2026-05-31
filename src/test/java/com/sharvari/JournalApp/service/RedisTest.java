package com.sharvari.JournalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Disabled
    @Test
    void testRedisConnection() {
        redisTemplate.opsForValue().set("email","gmail@email.com");
        Object salary = redisTemplate.opsForValue().get("salary");
        System.out.println(salary);
    }
}
