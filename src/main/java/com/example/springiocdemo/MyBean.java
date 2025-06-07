package com.example.springiocdemo;

import org.springframework.stereotype.Component;

@Component
public class MyBean {
    public void use() {
        System.out.println("This is a sample method in MyBean.");
    }
}