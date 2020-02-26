package com.skittles.buyticket.controller;

import com.skittles.buyticket.Timer.DataTask;
import com.skittles.buyticket.detailMapper.OrderDetailMapper;
import com.skittles.buyticket.detailMapper.SceneDetailMapper;
import com.skittles.buyticket.detailMapper.TestMapper;
import com.skittles.buyticket.mapper.SceneMapper;
import com.skittles.buyticket.mapper.UserMapper;


import com.skittles.buyticket.model.Scene;

import com.skittles.buyticket.test.MyObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Api(value = "测试模块", tags = "测试controller的控制")
@RestController
public class TestController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    SceneDetailMapper sceneDetailMapper;

    @Autowired
    TestMapper testMapper;
    @Autowired
    OrderDetailMapper orderDetailMapper;
    @Autowired
    SceneMapper sceneMapper;
    @Autowired
    DataTask dataTask;
    @Autowired
    JedisPool jedisPool;
    @Value("${zzr.hostname}")
    public String hostname;
    @ApiOperation(value = "第一个接口", notes = "这是第一个接口")
    @GetMapping(value = "/test")
    public String test(HttpServletRequest request) {
        return hostname;
    }

    @PostMapping(value = "/test1", produces = "application/json")
    public void test1(@RequestBody MyObject myObject) {
        System.out.println(myObject.code);
    }

    @RequestMapping("/test3")
    public String test3(){
        dataTask.updateTodayData();
        return "test3";
    }

    @RequestMapping("/test4")
    public String test4(HttpServletResponse response) throws IOException {
        response.sendRedirect("/test2.html");
        return null;
    }


    @RequestMapping("/testRedis")
    public String testRedis(){
        Jedis jedis=null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(15);
            jedis.set("testConnection","connection");
            jedis.close();
            return "连接正常";
        } catch (Exception e) {
            return "连接失败";
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    @RequestMapping(value = "/test/wechat", produces = "application/json")
    public void testWechat(String code) {
        System.out.println("成功回调");
        System.out.println(code);
    }


    /*局部处理异常*/
/*@ExceptionHandler(BindException.class)
    public Map<String,Object> exceptionHandler(Exception e){
        Map map=new HashMap();
        map.put("code","notExit");
        map.put("msg",e.getMessage());
        return map;
    }*/
}
