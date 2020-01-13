package com.skittles.buyticket.service.serviceImpl;

import com.skittles.buyticket.mapper.UserMapper;
import com.skittles.buyticket.model.MyUserDetail;
import com.skittles.buyticket.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        MyUserDetail userDetail = new MyUserDetail();
        User user = userMapper.selectByUserName(s);
        if(user!=null){
            userDetail.setUser(user);
            return userDetail;
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
