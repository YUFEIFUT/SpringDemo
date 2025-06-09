package com.example.transaction.required.dao;

import com.example.transaction.required.entity.User;

import java.util.List;

public interface UserDao {
    void insertUser(String name);
    List<User> findUserByName(String name);
}