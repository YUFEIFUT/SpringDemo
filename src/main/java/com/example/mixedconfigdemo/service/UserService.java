package com.example.mixedconfigdemo.service;

import com.example.mixedconfigdemo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // 注册为 Bean
public class UserService {

    private final UserDao userDao;

    @Autowired // 构造函数注入
    public UserService(UserDao userDao1) {
        this.userDao = userDao1;
    }

    public void registerUser() {
        userDao.saveUser();
    }
}