package com.example.springioc_circular_dependency_demo;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class BeanTwo implements Bean {
    private final Bean beanOne;

    // 构造器注入，依赖 BeanOne
    @Lazy
    public BeanTwo(Bean beanOne) {
        this.beanOne = beanOne;
    }
}