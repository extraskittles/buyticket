package com.skittles.buyticket.utils;

import com.skittles.buyticket.result.CommonResult;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {
    //通过请求获取用户ID
    public static int getIdByRequest(HttpServletRequest request){
        int id=0;
        String token=null;
        Map<String, Object> payload=null;
        Cookie[] cookies = request.getCookies();
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
        HttpHeaders headers = new HttpHeaders();
           /* headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);*/
        HttpEntity<Map<String,Object>> mapHttpEntity = new HttpEntity<>(params,headers);
        ResponseEntity<String> response = client.exchange(url, method, mapHttpEntity,String.class);
        return response.getBody();
    }
}
