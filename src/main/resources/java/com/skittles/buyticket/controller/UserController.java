package com.skittles.buyticket.controller;

import com.skittles.buyticket.mapper.UserMapper;
import com.skittles.buyticket.model.User;
import com.skittles.buyticket.param.MsgLoginParam;
import com.skittles.buyticket.result.CommonResult;
import com.skittles.buyticket.service.UserService;
import com.skittles.buyticket.service.serviceImpl.MyUserDetailService;
import com.skittles.buyticket.utils.HttpUtils;
import com.skittles.buyticket.utils.RXUtils;
import com.skittles.buyticket.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.HttpResource;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Random;

@Api(tags = "用户信息中心")
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    MyUserDetailService userDetailService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;


    @ApiOperation("登陆")
    @ApiImplicitParam("用户")
    @PostMapping(value = "/login", produces = "application/json")
    public CommonResult login(@Validated @RequestBody User user, HttpServletResponse response) {
        String token = userService.login(user);
        if (token == null) {
            return CommonResult.validateFailed();
        } else {
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);
            return CommonResult.success(token);
        }
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public CommonResult register(@Validated @RequestBody User user) {
        boolean flag = userService.register(user);
        if (flag) {
            return CommonResult.success();
        }
        return CommonResult.failed("用户名已被注册，请选择其他用户名");
    }


    @ApiOperation("注销")
    @ApiImplicitParam("用户")
    @GetMapping("/logout")
    public CommonResult logout(HttpServletResponse response) {
        return CommonResult.success();
    }


    @ApiOperation("查看用户信息")
    @GetMapping("")
    public CommonResult selectUser(HttpServletRequest request) {
        int id = HttpUtils.getIdByRequest(request);
        User user = userService.selectUser(id);

        return CommonResult.success(user);
    }

    @ApiOperation("修改用户信息")
    @PostMapping("")
    public CommonResult updataUser(@RequestBody User user, HttpServletRequest request) {
        int id = HttpUtils.getIdByRequest(request);
        int count = userService.updateUser(id, user);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("微信登陆")
    @ApiImplicitParam("用户")
    @GetMapping(value = "/wechatLogin")
    public CommonResult weChatlogin(String code, HttpServletResponse response) throws IOException {
        String token = userService.wechatLogin(code);
        if (token == null) {
            return CommonResult.validateFailed();
        } else {
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);
            response.sendRedirect("/index.html");
        }
        return null;
    }

    @ApiOperation("发送短信")
    @PostMapping(value = "/getMsgCode", produces = "application/json")
    public CommonResult getMsgCode(@Validated @NotNull @RequestBody String phoneNumber) {
        boolean flag = userService.getMsgCode(phoneNumber);
        if (flag) {
            return CommonResult.success();
        } else {
            return CommonResult.failed("手机不符合格式");
        }
    }

    @ApiOperation("短信登陆")
    @PostMapping(value = "/msgLogin", produces = "application/json")
    public CommonResult getMsgCode(@Validated @NotNull @RequestBody MsgLoginParam msgLoginParam, HttpServletResponse response) {
        String phoneNumber = msgLoginParam.getPhoneNumber();
        String msgCode = msgLoginParam.getMsgCode();
        String token = userService.msgLogin(phoneNumber, msgCode);
        if (token == null) {
            return CommonResult.validateFailed();
        } else {
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);
            return CommonResult.success(token);
        }
    }
}

