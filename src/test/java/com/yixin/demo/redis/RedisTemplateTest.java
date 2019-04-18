package com.yixin.demo.redis;

import com.yixin.alixjob.SpringbootApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author yaojie
 * @Description: TODO
 * @date 2018/9/299:26
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
public class RedisTemplateTest {


    @Resource

    RedisTemplate redisTemplate;



    @Test
    public void test_string(){

        redisTemplate.opsForValue().set("test_key00","test_key_value");


        String val = (String)redisTemplate.opsForValue().get("test_key00");

        System.out.println(val);


    }


}
