package com.example.springiocdemo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example.springiocdemo")
public class AppConfig {
    // 这里可以添加更多的配置方法，目前只做包扫描配置
}