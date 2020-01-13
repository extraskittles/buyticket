package com.skittles.buyticket.controller;

import com.skittles.buyticket.detailMapper.OrderDetailMapper;
import com.skittles.buyticket.detailMapper.SceneDetailMapper;
import com.skittles.buyticket.detailMapper.TestMapper;
import com.skittles.buyticket.detailModel.OrderDetail;
import com.skittles.buyticket.mapper.SceneMapper;
import com.skittles.buyticket.mapper.UserMapper;


import com.skittles.buyticket.detailModel.SceneDetail;
import com.skittles.buyticket.model.Scene;
import com.skittles.buyticket.model.User;
import com.skittles.buyticket.test.MyObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;


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

    @ApiOperation(value = "第一个接口", notes = "这是第一个接口")
    @GetMapping(value = "/test")
    public Object test() {
        return true;
    }

    @PostMapping(value = "/test1", produces = "application/json")
    public void test1(@RequestBody MyObject myObject) {
        System.out.println(myObject.code);
    }

    @RequestMapping
    public String test3(){

        return null;
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
