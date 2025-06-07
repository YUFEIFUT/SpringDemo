package com.example.injectionorder;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.injectionorder.config.AppConfig;

/*
执行顺序：

    构造器注入最先执行（在对象实例化时）

    字段注入和Setter注入在属性填充阶段执行（顺序取决于反射处理的顺序）

    @PostConstruct 方法最后执行，验证所有注入已完成

技术细节：

    构造器注入通过 AutowiredAnnotationBeanPostProcessor 处理

    字段和Setter注入在 populateBean() 阶段处理

    虽然字段注入和Setter注入在代码中看起来是分开的，但在Spring处理时都属于属性填充阶段

特殊说明：

    实际开发中不需要为字段注入显式添加setter方法（这里只是为了演示）

    字段注入和Setter注入的执行顺序在实际应用中通常不重要，因为它们属于同一生命周期阶段
 */
public class MainApp {
    public static void main(String[] args) {
        System.out.println("启动 Spring 容器...");
        AnnotationConfigApplicationContext context = 
                new AnnotationConfigApplicationContext(AppConfig.class);
        
        System.out.println("\n从容器获取 InjectionOrderService...");
        context.getBean("injectionOrderService");
        
        System.out.println("\n关闭 Spring 容器...");
        context.close();
    }
}