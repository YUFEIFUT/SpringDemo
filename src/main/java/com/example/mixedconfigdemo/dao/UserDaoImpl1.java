package com.example.mixedconfigdemo.dao;

import org.springframework.stereotype.Repository;

@Repository("userDao1") // 注册为 Bean，名称为 userDao1
public class UserDaoImpl1 implements UserDao {
    @Override
    public void saveUser() {
        System.out.println("UserDaoImpl1: Saving user...");
    }
}