package com.zz.service;

import com.zz.common.model.User;
import com.zz.common.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("正在provider中执行getUser()");
        return user;
    }
}
