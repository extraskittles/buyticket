package com.skittles.buyticket.utils;

import com.skittles.buyticket.result.CommonResult;
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
    public static int getIdByRequest(HttpServletRequest request){
        int id=0;
        String token=null;
        Map<String, Object> payload=null;
        Cookie[] cookies = request.getCookies();
        if(cookies==null){
            return id;
        }
        for(Cookie cookie:cookies){
            if("token".equals(cookie.getName())){
                token=cookie.getValue();
                try {
                    payload = JwtUtils.getPayLord(token);
                } catch (Exception e) {
                    return id;
                }
                if(payload==null){
                    return id;
                }
                    id =(int)payload.get("id");
            }
        }
        return id;
    }
    //服务器向微信服务器发送请求
    public static String sendHttpRequest(String url,HttpMethod method,Map<String,Object> params){
        RestTemplate client = new RestTemplate();
        client.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Map<String,Object>> mapHttpEntity = new HttpEntity<>(params,headers);
        ResponseEntity<String> response = client.exchange(url, method, mapHttpEntity,String.class);
        return response.getBody();
    }
}
