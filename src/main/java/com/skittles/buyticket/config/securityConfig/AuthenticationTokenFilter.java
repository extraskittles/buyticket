package com.skittles.buyticket.config.securityConfig;

import com.nimbusds.jose.JOSEException;
import com.skittles.buyticket.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userService; //用户信息service

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token=null;
        //检验token是否有效
        Cookie[] cookies = request.getCookies();
        if(cookies==null){
            chain.doFilter(request,response);
            return;
        }
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("token")){
                try {
                    if(JwtUtils.verifyToken(token=cookie.getValue()).get("code").equals(1)){
                        Map<String, Object> payLord = null;
                        try {
                            payLord = JwtUtils.getPayLord(token);
                        } catch (Exception e) {
                            chain.doFilter(request,response);
                            return;
                        }
                        String[] roles = ((String)payLord.get("roles")).split(",");
                        Collection<GrantedAuthority> authorities = new ArrayList<>();
                        for(String role:roles){
                            authorities.add(new SimpleGrantedAuthority(role));
                        }
                        Authentication authentication=new UsernamePasswordAuthenticationToken("UserDetail",null,authorities);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (JOSEException e) {
                    e.printStackTrace();
                }
                chain.doFilter(request,response);
                return;
            }
        }
        chain.doFilter(request,response);
        return;
    }
}
