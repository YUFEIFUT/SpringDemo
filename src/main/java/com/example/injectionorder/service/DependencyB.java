package com.example.injectionorder.service;

import org.springframework.stereotype.Component;

@Component
public class DependencyB {
    public DependencyB() {
        System.out.println("DependencyB 实例被创建");
    }
}