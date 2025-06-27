package com.example.ioc.dynamic_bean_register.registryppostprocessor;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Demo主类：演示如何通过BeanDefinitionRegistryPostProcessor动态注册Bean。
 * <p>
 * 流程说明：
 * 1. 启动Spring容器（传入DynamicBeanRegistryPostProcessor配置类）。
 * 2. 容器启动时，DynamicBeanRegistryPostProcessor会动态注册DynamicBean。
 * 3. 通过context.getBean(DynamicBean.class)获取到动态注册的Bean并调用其方法。
 * <p>
 * 注意事项：
 * - 动态注册的Bean与普通Bean等价，可以像普通Bean一样注入和获取。
 * - 本例仅用于演示动态注册流程，实际项目中可用于插件、扩展点等场景。
 */
public class DynamicBeanDemoMain {
    public static void main(String[] args) {
        // 创建Spring上下文，并自动执行BeanDefinitionRegistryPostProcessor
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DynamicBeanRegistryPostProcessor.class);
        // 获取动态注册的Bean
        DynamicBean dynamicBean = context.getBean(DynamicBean.class);
        // 调用Bean方法
        dynamicBean.sayHello();
        context.close();
    }
} 