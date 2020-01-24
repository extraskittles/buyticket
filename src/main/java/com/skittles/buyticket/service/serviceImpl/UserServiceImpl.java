package com.skittles.buyticket.service.serviceImpl;

import com.nimbusds.jose.JOSEException;
import com.skittles.buyticket.mapper.UserMapper;
import com.skittles.buyticket.mapper.WechatUserMapper;
import com.skittles.buyticket.model.MyUserDetail;
import com.skittles.buyticket.model.User;
import com.skittles.buyticket.model.WechatUser;
import com.skittles.buyticket.service.UserService;
import com.skittles.buyticket.utils.HttpUtils;
import com.skittles.buyticket.utils.JsonUtils;
import com.skittles.buyticket.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserMapper userMapper;


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
        String token=null;
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx290c9f6319d3532c&secret=7aaa74e1564eef0d9b0c2bade4bfe9dd&code="+code+"&grant_type=authorization_code";
        Map<String,Object> params =new HashMap<>();
        String strBody = HttpUtils.sendHttpRequest(url, HttpMethod.GET, params);
        Map<String, Object> body = JsonUtils.toMap(strBody);
        String openid=(String) body.get("openid");
        //判断是否已经微信登陆
        if(openid!=null){
            //判断是否第一次登陆，第一次登陆将openid写入数据库
            User dataUser = userMapper.selectByOpenid(openid);
            Integer id;
            if(dataUser==null){
                User user = new User();
                user.setOpenid(openid);
                userMapper.insertSelective(user);
                id = user.getId();
            }else {
                id=dataUser.getId();
            }
            Map<String,Object> map =new HashMap<>();
            map.put("id",id);
            map.put("roles","ROLE_USER");
            try {
                token = JwtUtils.createToken(map);
            } catch (JOSEException e) {
                e.printStackTrace();
            }
        }
        return token;
    }
}
