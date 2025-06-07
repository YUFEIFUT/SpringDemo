package com.example.lifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.lifecycle.config.AppConfig;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("Spring 容器启动...");
        AnnotationConfigApplicationContext context = 
                new AnnotationConfigApplicationContext(AppConfig.class);
        
        System.out.println("\nSpring 容器运行中...");
        Object bean = context.getBean("lifecycleDemoBean");
        System.out.println("获取到的 Bean: " + bean);
        
        System.out.println("\nSpring 容器关闭...");
        context.close();
        System.out.println("Spring 容器已关闭");
    }
}