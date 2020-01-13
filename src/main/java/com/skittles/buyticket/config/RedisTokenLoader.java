/*
package com.skittles.buyticket.config;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.weixin4j.loader.ITokenLoader;
import org.weixin4j.model.base.Token;

import java.util.concurrent.TimeUnit;

@Component
public class RedisTokenLoader implements ITokenLoader {
    String appid;
    private final String ACCESS_TOKEN_KEY = "ats_wx100000000001";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public RedisTokenLoader(String appid) {
        this.appid = appid;
    }

    @Override
    public Token get() {
        String accessToken = stringRedisTemplate.opsForValue().get(ACCESS_TOKEN_KEY);
        return JSON.parseObject(accessToken, Token.class);
    }

    @Override
    public void refresh(Token token) {
        String accessToken = JSON.toJSONString(token);
        //ticket.getExpires_in() - 600L，是为了提前10分钟过期
        stringRedisTemplate.opsForValue().set(ACCESS_TOKEN_KEY, accessToken, token.getExpires_in() - 600L, TimeUnit.SECONDS);
    }
}
*/
