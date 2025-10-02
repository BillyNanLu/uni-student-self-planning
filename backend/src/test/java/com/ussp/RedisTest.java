package com.ussp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest // @RunWith(SpringRunner.class) 在测试类上加上了这个注解，那么将来单元测试方法执行前会初始化Spring容器
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testSet() {
        // 往redis中存储一个键值对   SpringRedisTemplate
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();

        stringStringValueOperations.set("name", "nan19");
        stringStringValueOperations.set("id", "1", 15, TimeUnit.SECONDS);
    }

    @Test
    public void testGet() {
        // 从redis中获取一个键值对
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        String name = stringStringValueOperations.get("name");
        System.out.println(name);
    }

}
