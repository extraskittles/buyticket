package com.skittles.buyticket.utils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {
    /**
     * 单个连接
     *
     * @param host
     * @param port
     * @return
     */
    public static Jedis cli_single(String host, int port) {
        try {
            Jedis jedis = new Jedis(host, port);
            jedis.auth("skittles");
            return jedis;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 连接池
     *
     * @return
     */
  /*  @Bean*/
    public Jedis cli_pool() {
        String host="111.231.237.220";
        int port=6379;
        JedisPoolConfig config = new JedisPoolConfig();
        // 最大连接数
        config.setMaxTotal(10);
        // 最大连接空闲数
        config.setMaxIdle(2);
        JedisPool jedisPool = new JedisPool(config, host, port);
        try{
            Jedis resource = jedisPool.getResource();
            resource.auth("skittles");
            return resource;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}



