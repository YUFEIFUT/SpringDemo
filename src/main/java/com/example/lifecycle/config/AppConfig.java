package com.example.lifecycle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.lifecycle.bean.LifecycleDemoBean;
import com.example.lifecycle.bean.MyBeanPostProcessor;

@Configuration
public class AppConfig {
    
    @Bean(initMethod = "customInit", destroyMethod = "customDestroy")
    public LifecycleDemoBean lifecycleDemoBean() {
        return new LifecycleDemoBean();
    }
    
    @Bean
    public MyBeanPostProcessor myBeanPostProcessor() {
        return new MyBeanPostProcessor();
    }
}