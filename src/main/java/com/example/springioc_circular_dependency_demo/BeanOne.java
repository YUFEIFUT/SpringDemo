package com.example.springioc_circular_dependency_demo;

import org.springframework.stereotype.Component;

@Component
public class BeanOne implements Bean {
    private final Bean beanTwo;

    // 构造器注入，依赖 BeanTwo
    public BeanOne(Bean beanTwo) {
        this.beanTwo = beanTwo;
    }
}