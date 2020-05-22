package com.skittles.buyticket.config;


import com.skittles.buyticket.config.securityConfig.*;
import com.skittles.buyticket.service.serviceImpl.MyUserDetailService;
import org.apache.catalina.webresources.JarWarResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    BackDoorAuthenticationProvider backDoorAuthenticationProvider;
    @Autowired
    MyUserDetailService userDetailService;
    @Autowired
    AuthenticationTokenFilter authenticationTokenFilter;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //将自定义验证类注册进去
        auth.authenticationProvider(backDoorAuthenticationProvider);
        //加入数据库验证类，下面的语句实际上在验证链中加入了一个DaoAuthenticationProvider
        auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

   /* *
     * 匹配 "/","/index" 路径，不需要权限即可访问
     * 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限
     * 匹配 "/admin" 及其以下所有路径，都需要 "ADMIN" 权限
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/test","/user/login","/user/register","/user/msgLogin","/user/wechatLogin","/user/getMsgCode","user/msgLogin").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/merchant/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .logout().logoutUrl("/user/logout").logoutSuccessHandler(new MyLogoutSuccessHandler())
                .deleteCookies("token")
                //拦截的url会重定向到loginPage；请求会交到login处理；成功到defaultSuccessUrl处理，失败由failureForwardUrl处理；
               /* .formLogin().loginProcessingUrl("/login").failureForwardUrl("/loginFailure").defaultSuccessUrl("/loginSuccess")*/
                //1.自定义参数名称，与login.html中的参数对应
               /* .usernameParameter("name").passwordParameter("password")*/
              /*  .and()*/
               /* .logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccess")
                .and()*/
                //添加token验证的filter
                .and()
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
//禁用缓存
        http.headers().cacheControl();
        //添加自定义异常入口，处理entryPoint异常和accessDeny异常
        http.exceptionHandling().authenticationEntryPoint(new MyEntryPointHandler())
                .accessDeniedHandler(new MyAccessDeniedHandler());
    }
    @Bean
    PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}



//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf()// 由于使用的是JWT，我们这里不需要csrf
//                .disable()
//                .sessionManagement()// 基于token，所以不需要session
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                //指定跳转的logout的url
//                .logout()
//                .logoutUrl("/admin/logout")
//                .logoutSuccessUrl("/admin/logoutSuccess")
//                .invalidateHttpSession(true)
//                .deleteCookies("token")
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, // 允许对于网站静态资源的无授权访问
//                        "/",
//                        "/*.html",
//                        "/favicon.ico",
//                        "/**/*.html",
//                        "/**/*.css",
//                        "/**/*.js",
//                        "/swagger-resources/**",
//                        "/v2/api-docs/**",
//                        "/webjars/springfox-swagger-ui/**"
//                )
//                .permitAll()
//                .antMatchers("/admin/login", "/admin/register")// 对登录注册要允许匿名访问
//                .permitAll()
//                .antMatchers(HttpMethod.OPTIONS)//跨域请求会先进行一次options请求
//                .permitAll()
////              .antMatchers("/**")//测试时全部运行访问
//                //             .permitAll()
//                .anyRequest()// 除上面外的所有请求全部需要鉴权认证
//                .authenticated();
//
//        // 禁用缓存
//        httpSecurity.headers().cacheControl();
//        // 添加JWT filter
//        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//        //添加自定义未授权和未登录结果返回
//        httpSecurity.exceptionHandling()
//                .accessDeniedHandler(restfulAccessDeniedHandler)
//                .authenticationEntryPoint(restAuthenticationEntryPoint);
//    }