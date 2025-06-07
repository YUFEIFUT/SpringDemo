package com.example.springioc_interface_dependency_demo;

import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl1 implements UserDao {
    @Override
    public void saveUser() {
        System.out.println("UserDaoImpl1: Saving user...");
    }
}