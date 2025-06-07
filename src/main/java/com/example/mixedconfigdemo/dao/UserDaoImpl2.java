package com.example.mixedconfigdemo.dao;

public class UserDaoImpl2 implements UserDao {
    @Override
    public void saveUser() {
        System.out.println("UserDaoImpl2: Saving user...");
    }
}