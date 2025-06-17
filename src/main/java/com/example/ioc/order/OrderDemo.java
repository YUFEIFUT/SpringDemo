// 主运行类：com.example.ioc.order.OrderDemo
package com.example.ioc.order;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
我自己修改了 Order 注解中的值，但是发现基本不会改变 BeanA 和 BeanB ，ServiceA，ServiceB 的相对构造方法的执行顺序，而只会在 Container 中的 beanList 中的 bean 有体现顺序
 */
public class OrderDemo {
    public static void main(String[] args) {
        // 启动Spring容器
        try (AnnotationConfigApplicationContext context = 
             new AnnotationConfigApplicationContext(OrderConfig.class)) {
            System.out.println("\nSpring容器启动完成");
        }
    }
}