package com.example.springioc_interface_dependency_demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example.springioc_interface_dependency_demo")
public class AppConfig {

    // 这里可以根据需求选择注入哪个UserDao实现类
    // 例如当前返回UserDaoImpl1，若想切换为UserDaoImpl2，只需修改这里的返回值
    @Bean
    public UserDao userDao() {
        return new UserDaoImpl2();
    }
}