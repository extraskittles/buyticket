package com.skittles.buyticket.test;


import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


public class TestMybatis {
    @Autowired
    RedisTemplate redisTemplate;
    public static void main(String[] args) {
        List<String> list=new ArrayList<String>();
        list.add("3");
        list.remove("3");
        System.out.println(list);
    }
}
