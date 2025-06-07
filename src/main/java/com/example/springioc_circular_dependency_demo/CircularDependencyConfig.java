package com.example.springioc_circular_dependency_demo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration  // 标记为 Spring 配置类
@ComponentScan(basePackages = "com.example.springioc_circular_dependency_demo")  // 扫描当前包下的组件
public class CircularDependencyConfig {
}