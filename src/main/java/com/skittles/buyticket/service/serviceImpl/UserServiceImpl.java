package com.skittles.buyticket.service.serviceImpl;

import com.nimbusds.jose.JOSEException;
import com.skittles.buyticket.mapper.UserMapper;
import com.skittles.buyticket.mapper.WechatUserMapper;
import com.skittles.buyticket.model.MyUserDetail;
import com.skittles.buyticket.model.User;
import com.skittles.buyticket.model.WechatUser;
import com.skittles.buyticket.service.UserService;
import com.skittles.buyticket.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserMapper userMapper;
    @Autowired
    Jedis jedis;


    @Override
    public String login(User user) {
        String token = null;
        String name = user.getName();
        String password = user.getPassword();

        MyUserDetail userDetails = null;
        try {
            userDetails = (MyUserDetail) userDetailsService.loadUserByUsername(name);
        } catch (UsernameNotFoundException e) {
            return token;
        }

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            Map<String, Object> map = new HashMap<>();
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            String roles = userMapper.selectByUserName(name).getRoles();
            map.put("roles", roles);
            map.put("id", userDetails.getUser().getId());
            try {
                token = JwtUtils.createToken(map);
            } catch (JOSEException e) {
                e.printStackTrace();
            }
        }
        return token;
    }

    @Override
    public boolean register(User user) {
        User dataUser = userMapper.selectByUserName(user.getName());
        if (dataUser != null) {
            return false;
        }
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setRoles("ROLE_USER");
        user.setPassword(encodePassword);
        userMapper.insert(user);
        return true;
    }

    @Override
    public User selectUser(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateUser(int id, User user) {
        user.setId(id);
        user.setPassword(null);
        user.setRoles(null);
        int count = userMapper.updateByPrimaryKeySelective(user);
        return count;
    }


    @Override
    public String wechatLogin(String code) {
        String token = null;
        //获取网页授权的access_token和openid
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx290c9f6319d3532c&secret=7aaa74e1564eef0d9b0c2bade4bfe9dd&code=" + code + "&grant_type=authorization_code";
        Map<String, Object> params = new HashMap<>();
        String strBody = HttpUtils.sendHttpRequest(url, HttpMethod.GET, params);
        Map<String, Object> body = JsonUtils.toMap(strBody);
        String openid = (String) body.get("openid");
        String access_token = (String) body.get("access_token");
        //获取用户信息
        String url2 = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
       /* params2.put("access_token",access_token);
        params2.put("openid",openid);
        params2.put("lang","zh_CN");*/
        String strBody2 = HttpUtils.sendHttpRequest(url2, HttpMethod.GET, params);
        Map<String, Object> body2 = JsonUtils.toMap(strBody2);
        String name = (String) body2.get("nickname");
        //判断是否已经微信登陆
        if (openid != null) {
            //判断是否第一次登陆，第一次登陆将openid写入数据库
            User dataUser = userMapper.selectByOpenid(openid);
            Integer id;
            User user = new User();
            //第一次则插入用户信息
            if (dataUser == null) {
                user.setOpenid(openid);
                user.setPoints(2000000);
                user.setRoles("ROLE_USER");
                user.setName(name);
                userMapper.insert(user);
                id = user.getId();
                //非第一次更新用户信息
            } else {
                user.setOpenid(openid);
                user.setName(name);
                user.setId(dataUser.getId());
                userMapper.updateByPrimaryKeySelective(user);
                id = dataUser.getId();
            }
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("roles", "ROLE_USER");
            try {
                token = JwtUtils.createToken(map);
            } catch (JOSEException e) {
                e.printStackTrace();
            }
        }
        return token;
    }

    @Override
    public boolean getMsgCode(String phoneNumber) {
        //判断手机正则是否符合
        boolean flag = RXUtils.phoneNumMatch(phoneNumber);
        if (flag) {
            //生成随机6位数字
            Random random = new Random();
            String str = random.nextInt(999999)+ "";
            //储存在redis
            jedis.set(phoneNumber,str);
            jedis.expire(phoneNumber, 600);
            //发送短信
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String msgLogin(String phoneNumber, String msgCode) {
        String token=null;
        String s = jedis.get(phoneNumber);
        if (msgCode.equals(s)) {
            //判断是否第一次登陆，第一次登陆需将手机号写入数据库
            User user = userMapper.selectByUserName(phoneNumber);
            if (user == null) {
                user=new User();
                user.setName(phoneNumber);
                user.setRoles("ROLE_USER");
                user.setPoints(200000);
                userMapper.insert(user);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("roles", "ROLE_USER");
            map.put("id", user.getId());
            try {
                token = JwtUtils.createToken(map);
            } catch (JOSEException e) {
                e.printStackTrace();
            }

        }
        return token;
    }
}
