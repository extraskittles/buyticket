package com.skittles.buyticket.test;


import com.foxinmy.weixin4j.exception.WeixinException;

import com.foxinmy.weixin4j.mp.WeixinProxy;

import com.foxinmy.weixin4j.mp.model.User;

import com.skittles.buyticket.mapper.UserMapper;
import com.skittles.buyticket.model.Scene;
import com.skittles.buyticket.utils.*;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.sql.Time;
import java.util.*;


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
        /*Jedis jedis = RedisUtils.cli_pool();
        String aa = jedis.get("AA");
        jedis.expire("AA",1000);
        System.out.println(aa);*/
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

    @Test
    public void test5(){
        new Timer("testTime").schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("计数");
            }
        },1000,2000);
    }

    public static void main(String[] args) {
        Timetest();
    }

    public static void Timetest(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("time测试");
            }
        },1000,1000);
    }
    @Test
    public void test6(){
        Scene scene = new Scene();
        Date date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        long time=date.getTime()+(long)(6*60*60*1000);
        int sceneCount=1;
        //遍历3间影院
        for(int h=1;h<4;h++){
            scene.setCinemaId(h);
            //遍历该影院的3个影厅
            for(int j=1;j<4;j++){
                scene.setHallId(j);
                scene.setMovieId(4-j);
                //遍历每个影厅的8个时间段
                for(int k=1;k<9;k++){
                    time=time+(long)(2*60*60*1000);
                    date.setTime(time);
                    if(h==1){
                        scene.setName("光大影院场次"+sceneCount);
                    }else if(h==2){
                        scene.setName("皓天影院场次"+sceneCount);
                    }else if(h==3){
                        scene.setName("勒流影院场次"+sceneCount);
                    }
                    sceneCount++;
                    scene.setDatetime(date);
                    System.out.println(scene);
                }
                date.setHours(0);
                date.setMinutes(0);
                date.setSeconds(0);
                //遍历完一个厅后，让time回到6:20或者6:40,6:00
                if(j==1){
                    time=date.getTime()+(long)(6*60*60*1000+20*60*1000);
                }else if(j==2){
                    time=date.getTime()+(long)(6*60*60*1000+40*60*1000);
                }else if(j==3){
                    time=date.getTime()+(long)(6*60*60*1000);
                }
            }
            sceneCount=1;
        }

    }
    @Test
    public void test7(){
        StringUtils.sitNumberStr(20);
    }
}
