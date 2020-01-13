package com.skittles.buyticket.utils;

import com.skittles.buyticket.result.CommonResult;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class HttpUtils {
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
}
