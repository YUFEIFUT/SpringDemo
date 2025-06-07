package com.example.mixedconfigdemo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan(basePackages = "com.example.mixedconfigdemo") // 扫描注解组件
@ImportResource("classpath:SpringConfig.xml") // 导入 XML 配置
public class AppConfig {

}