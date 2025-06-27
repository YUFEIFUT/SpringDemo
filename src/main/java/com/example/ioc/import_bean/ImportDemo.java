package com.example.ioc.import_bean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Import注解演示主类
 * 
 * 演示目标：
 * 1. 展示@Import注解如何将普通类导入到Spring容器中
 * 2. 验证被导入的类是否真的被Spring管理
 * 3. 展示如何从容器中获取被导入的Bean
 */
public class ImportDemo {
    
    public static void main(String[] args) {
        System.out.println("=== @Import注解演示开始 ===\n");
        
        // 创建Spring容器，使用ImportConfig配置类
        AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(ImportConfig.class);
        
        System.out.println("\n=== 从Spring容器中获取被@Import导入的Bean ===\n");
        
        // 获取UserService Bean
        UserService userService = context.getBean(UserService.class);
        System.out.println("获取到UserService Bean: " + userService);
        userService.sayHello();
        
        // 获取OrderService Bean
        OrderService orderService = context.getBean(OrderService.class);
        System.out.println("获取到OrderService Bean: " + orderService);
        orderService.processOrder();
        
        // 通过Bean名称获取（我实际运行，默认名称是全限定类名）
        UserService userServiceByName = (UserService) context.getBean("com.example.ioc.import_bean.UserService");
        OrderService orderServiceByName = (OrderService) context.getBean("com.example.ioc.import_bean.OrderService");
        
        System.out.println("\n=== 通过Bean名称获取验证 ===\n");
        System.out.println("通过名称获取UserService: " + userServiceByName);
        System.out.println("通过名称获取OrderService: " + orderServiceByName);
        
        // 验证是否为同一个实例
        System.out.println("\n=== 验证Bean实例 ===\n");
        System.out.println("UserService是否为同一个实例: " + (userService == userServiceByName));
        System.out.println("OrderService是否为同一个实例: " + (orderService == orderServiceByName));
        
        // 查看容器中所有的Bean名称
        System.out.println("\n=== 容器中所有的Bean名称 ===\n");
        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println("Bean名称: " + beanName);
        }
        
        // 关闭容器
        context.close();
        
        System.out.println("\n=== @Import注解演示结束 ===");
    }
} 