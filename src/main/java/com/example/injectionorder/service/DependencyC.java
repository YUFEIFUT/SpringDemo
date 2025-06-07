package com.example.injectionorder.service;

import org.springframework.stereotype.Component;

@Component
public class DependencyC {
    public DependencyC() {
        System.out.println("DependencyC 实例被创建");
    }
}