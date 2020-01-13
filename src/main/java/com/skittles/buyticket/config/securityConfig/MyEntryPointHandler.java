package com.skittles.buyticket.config.securityConfig;
import com.google.gson.Gson;
import com.skittles.buyticket.result.CommonResult;
import com.skittles.buyticket.result.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
//未登录而访问受保护资源的处理
@Component
public class MyEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

      /* httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/entryPoint");*/
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        Gson gson = new Gson();
        String json = gson.toJson(ResultCode.UNAUTHORIZED);
        httpServletResponse.getWriter().println(json);
        httpServletResponse.getWriter().flush();
    }
}
