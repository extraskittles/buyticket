package com.skittles.buyticket.test;


import com.foxinmy.weixin4j.exception.WeixinException;

import com.foxinmy.weixin4j.mp.WeixinProxy;

import com.foxinmy.weixin4j.mp.model.User;

import org.junit.Test;

import java.util.List;


public class TestMain {


    @Test
    public void test1() {
        WeixinProxy weixinProxy = new WeixinProxy();
        List<User> users = null;
        try {
            users = weixinProxy.getAllFollowing();
        } catch (WeixinException e) {
            e.printStackTrace();
        }
        System.out.println(users);
    }
}
