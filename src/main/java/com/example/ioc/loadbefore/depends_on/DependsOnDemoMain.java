package com.example.ioc.loadbefore.depends_on;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

/*
 主启动类

 DependsOn: 对于应用之外的二方或者三方库来说，因为我们不能修改外部库的代码，如果想要二方库的Bean在初始化之前就初始化我们内部的某个bean，就不能用第一种直接依赖的方式，可以使用@DependsOn注解来完成，如下代码所示：
 */
public class DependsOnDemoMain {
    public static void main(String[] args) {
        // 启动Spring容器并加载配置类
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanOrderConfiguration.class)) {
            // 输出容器中的Bean列表（可选，用于验证Bean存在性）
            String[] beanDefinitionNames = context.getBeanDefinitionNames();
            System.out.println("容器中存在的BeanDefinition：");
            for (String name : beanDefinitionNames) {
                System.out.println("- " + name);
            }

            // 获取BeanA实例（触发依赖检查）
            BeanA beanA = context.getBean(BeanA.class);
            System.out.println("获取到BeanA实例：" + beanA);
        }
    }
}

/**
 * 被依赖的Bean（会先初始化）
 */
@Component
class BeanB {
    public BeanB() {
        System.out.println("【BeanB】初始化");
    }
}

/**
 * 依赖BeanB的目标Bean
 */
class BeanA {
    public BeanA() {
        System.out.println("【BeanA】初始化");
    }

    @Override
    public String toString() {
        return "BeanA{initialized=true}";
    }
}

/**
 * 配置类（关键逻辑所在）
 */
@Configuration
@ComponentScan(basePackages = "com.example.ioc.loadbefore.depends_on") // 确保扫描子包
class BeanOrderConfiguration {

    @Bean
    @DependsOn("beanB") // 指定依赖BeanB（注意这里的名称是BeanB的默认Bean名，即类名首字母小写）
    public BeanA beanA() {
        System.out.println("【配置类】正在创建BeanA实例");
        return new BeanA();
    }
}