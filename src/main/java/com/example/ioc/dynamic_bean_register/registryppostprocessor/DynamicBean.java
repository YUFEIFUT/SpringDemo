package com.example.ioc.dynamic_bean_register.registryppostprocessor;

/**
 * 这是一个用于演示动态注册的简单Bean。
 * 通过BeanDefinitionRegistryPostProcessor在Spring容器启动时动态注册。
 */
public class DynamicBean {
    /**
     * 打印一句问候语，证明该Bean已被成功注册和获取。
     */
    public void sayHello() {
        System.out.println("Hello from DynamicBean! 我是动态注册的Bean");
    }
} 