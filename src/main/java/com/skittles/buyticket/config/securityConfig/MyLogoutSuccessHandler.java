package com.skittles.buyticket.config.securityConfig;

import com.google.gson.Gson;
import com.skittles.buyticket.result.CommonResult;
import com.skittles.buyticket.result.ResultCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        Gson gson = new Gson();
        String json = gson.toJson(CommonResult.success());
        httpServletResponse.getWriter().println(json);
        httpServletResponse.getWriter().flush();
    }
}
