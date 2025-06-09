package com.example.nonpublic;

import org.springframework.beans.factory.support.RootBeanDefinition;
import java.lang.reflect.Modifier;

// 在 RootBeanDefinition 的父类 AbstractBeanDefinition 中，明确设置了 private boolean nonPublicAccessAllowed = true; 所以不会异常的
public class UltimateTest {

    public static void main(String[] args) {
        System.out.println("--- Ultimate Test Start ---");

        // 1. 获取我们那个非公有 Bean 的 Class 对象
        // 注意：这里我们用 Class.forName() 来模拟 Spring 发现类的过程
        // 为了能找到 package-private 的类，需要写全限定名
        Class<?> beanClass;
        try {
            beanClass = Class.forName("com.example.nonpublic.NonPublicBean");
            System.out.println("Successfully got class: " + beanClass.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // 2. 创建一个模拟的 BeanDefinition
        // RootBeanDefinition 是 Spring 中最完整的 Bean 定义信息类
        RootBeanDefinition mbd = new RootBeanDefinition(beanClass);
        // 可以手动设置这里来模拟
        mbd.setNonPublicAccessAllowed(false);
        System.out.println("Created a RootBeanDefinition for the class.");

        // 3. 核心：我们来手动复刻 Spring 源码中的那个 if 判断！
        System.out.println("\n--- Simulating Spring's internal check ---");

        boolean isPublic = Modifier.isPublic(beanClass.getModifiers());
        boolean nonPublicAccessAllowed = mbd.isNonPublicAccessAllowed();

        System.out.println("Is the bean class public? -> " + isPublic);
        System.out.println("Is non-public access allowed by BeanDefinition? -> " + nonPublicAccessAllowed);

        // 这就是源码中的那段逻辑
        if (beanClass != null && !isPublic && !nonPublicAccessAllowed) {
            System.out.println("\n>>> SUCCESS! The 'if' condition is MET. An exception would be thrown here.");
            System.out.println(">>> Bean class isn't public, and non-public access not allowed: " + beanClass.getName());
        } else {
            System.out.println("\n>>> FAILED! The 'if' condition was NOT met. Spring would proceed.");
            System.out.println("    - Condition 1 (!isPublic): " + (!isPublic));
            System.out.println("    - Condition 2 (!nonPublicAccessAllowed): " + (!nonPublicAccessAllowed));
        }

        System.out.println("\n--- Ultimate Test End ---");
    }
}