package com.skittles.buyticket.config.securityConfig;

import com.google.gson.Gson;
import com.skittles.buyticket.result.CommonResult;
import net.minidev.json.JSONUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
//没有足够权限访问资源的处理
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        Gson gson = new Gson();
        String json = gson.toJson(CommonResult.forbidden());
        httpServletResponse.getWriter().println(json);
        httpServletResponse.getWriter().flush();
        HttpServletRequest request;
    }
}
