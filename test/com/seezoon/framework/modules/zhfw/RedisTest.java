package com.seezoon.framework.modules.zhfw;

import com.seezoon.framework.common.context.test.BaseJunitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by zengqy on 2018/7/17.
 */
public class RedisTest   extends BaseJunitTest {

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Test
    public void testGetRedisValue() {
       // redisTemplate.opsForValue().set("wt_bffwl",new Integer(1378888));
        String tmp  = redisTemplate.opsForValue().get("wt_bffwl");




        System.out.println(tmp);
    }
}
