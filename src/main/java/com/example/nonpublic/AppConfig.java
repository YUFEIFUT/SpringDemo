package com.example.nonpublic;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example.nonpublic") // 启用组件扫描
public class AppConfig {
    // 之前 @Bean 方法的内容被移除
}