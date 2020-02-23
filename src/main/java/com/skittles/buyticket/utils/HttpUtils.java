package com.skittles.buyticket.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.skittles.buyticket.result.CommonResult;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {
    //通过请求获取用户ID
    public static int getIdByRequest(HttpServletRequest request) {
        int id = 0;
        String token = null;
        Map<String, Object> payload = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return id;
        }
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                token = cookie.getValue();
                try {
                    payload = JwtUtils.getPayLord(token);
                } catch (Exception e) {
                    return id;
                }
                if (payload == null) {
                    return id;
                }
                id = (int) payload.get("id");
            }
        }
        return id;
    }

    //服务器向微信服务器发送请求
    public static Map<String,Object> sendGet(String url) {
        RestTemplate client = new RestTemplate();
        client.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        ResponseEntity<String> bodyStr = client.getForEntity(url,String.class);
        String body = bodyStr.getBody();
        Gson gson = new Gson();
        Map map = gson.fromJson(body, Map.class);
        return map;
    }

    public static Map<String,Object> sendPost(String url, Map<String, Object> params){
        RestTemplate client = new RestTemplate();
        client.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        HttpHeaders headers = new HttpHeaders();
        Gson gson = new Gson();
        String s = gson.toJson(params, Map.class);
        HttpEntity<Map<String, Object>> mapHttpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<Map> map = client.postForEntity(url, mapHttpEntity, Map.class);
        return map.getBody();
    }
}
