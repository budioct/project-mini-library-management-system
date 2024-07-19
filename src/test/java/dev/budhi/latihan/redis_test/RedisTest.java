package dev.budhi.latihan.redis_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void redisTemplateTest(){

        Assertions.assertNotNull(redisTemplate);

    }

    @Test
    void operationStringRedisTest() throws InterruptedException {

        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        operations.set("name", "Budhi", Duration.ofSeconds(2));
        Assertions.assertEquals("Budhi", operations.get("name"));

        Thread.sleep(Duration.ofSeconds(3));
        Assertions.assertNull(operations.get("name"));

    }


}
