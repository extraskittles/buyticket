package com.skittles.buyticket.service;

import com.skittles.buyticket.model.User;
public interface UserService {
     String login(User user);
     boolean register(User user);
     User selectUser(int id);
     int updateUser(int id,User user);
}
