package com.skittles.buyticket.test;


import com.foxinmy.weixin4j.exception.WeixinException;

import com.foxinmy.weixin4j.mp.WeixinProxy;

import com.foxinmy.weixin4j.mp.model.User;

import com.skittles.buyticket.mapper.UserMapper;
import com.skittles.buyticket.model.Scene;
import com.skittles.buyticket.utils.*;
import org.junit.Test;
import org.springframework.http.*;


import java.util.*;


public class TestMain {


    @Test
    public void test1() {
        String url="http://zhouzhaorong.xyz/order/confirmOrder";
        Map<String,Object> map=new HashMap<>();
        map.put("sceneId",38035);
        map.put("sitNumbers","2");
        Map<String, Object> map1 = HttpUtils.sendPost(url,map);
        System.out.println(map1);
    }

    @Test
    public void test2() {
        String url="http://zhouzhaorong.xyz/order/selectMovieById?movieId=2";
        Map<String, Object> map = HttpUtils.sendGet(url);
        System.out.println(map);
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

    }

    @Test
    public void test5(){

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
