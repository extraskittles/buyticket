package com.skittles.buyticket.test;


import com.foxinmy.weixin4j.exception.WeixinException;

import com.foxinmy.weixin4j.mp.WeixinProxy;

import com.foxinmy.weixin4j.mp.model.User;

import com.skittles.buyticket.utils.HttpUtils;
import com.skittles.buyticket.utils.JsonUtils;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @Test
    public void test2() {
        /*Map<String,Object>params =new HashMap<>();
        Map body = HttpUtils.sendHttpRequest("http://zhouzhaorong.xyz/test3", HttpMethod.GET, params);
        System.out.println(body.get("11"));*/
    }
    @Test
    public void test3(){
        String str="{\n" +
                "  \"access_token\":\"ACCESS_TOKEN\",\n" +
                "  \"expires_in\":7200,\n" +
                "  \"refresh_token\":\"REFRESH_TOKEN\",\n" +
                "  \"openid\":\"OPENID\",\n" +
                "  \"scope\":\"SCOPE\" \n" +
                "}";
        Map<String, Object> map = JsonUtils.toMap(str);
        System.out.println(map.get("openid"));
    }

    @Test
    public void test4(){
        String s = HttpUtils.sendHttpRequest("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx290c9f6319d3532c&redirect_uri=http://zhouzhaorong.xyz/user/wechatLogin&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect", HttpMethod.GET, new HashMap<>());
        System.out.println(s);
    }
}
