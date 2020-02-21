package com.skittles.buyticket.controller;

import com.skittles.buyticket.mapper.UserMapper;
import com.skittles.buyticket.model.User;
import com.skittles.buyticket.result.CommonResult;
import com.skittles.buyticket.service.UserService;
import com.skittles.buyticket.service.serviceImpl.MyUserDetailService;
import com.skittles.buyticket.utils.HttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.HttpResource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    @CrossOrigin(origins = "*")
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
}

