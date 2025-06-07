package com.example.springioc_interface_dependency_demo;

import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl2 implements UserDao {
    @Override
    public void saveUser() {
        System.out.println("UserDaoImpl2: Saving user...");
    }
}