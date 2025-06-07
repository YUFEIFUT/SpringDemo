package com.example.springioc_circular_dependency_demo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CircularDependencyTest {
    public static void main(String[] args) {
        try {
            // 用注解配置类启动 Spring 容器
            AnnotationConfigApplicationContext context = 
                new AnnotationConfigApplicationContext(CircularDependencyConfig.class);
            
            // 尝试获取 Bean，触发循环依赖检查
            BeanOne beanOne = context.getBean(BeanOne.class);
            System.out.println("BeanOne 已创建: " + beanOne);
        } catch (Exception e) {
            // Spring 会抛出 BeanCurrentlyInCreationException 异常
            System.err.println("发生循环依赖异常: " + e.getMessage());
            e.printStackTrace();
        }
    }
}