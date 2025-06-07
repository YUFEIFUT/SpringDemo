package com.example.injectionorder.service;

import org.springframework.stereotype.Component;

@Component
public class DependencyA {
    public DependencyA() {
        System.out.println("DependencyA 实例被创建");
    }
}