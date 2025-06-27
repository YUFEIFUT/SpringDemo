package com.example.ioc.import_bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 配置类 - 使用@Import注解导入其他类到Spring容器中
 * 
 * @Import注解的作用：
 * 1. 将指定的类导入到Spring容器中，使其成为Spring管理的Bean
 * 2. 可以导入普通类、配置类、ImportSelector实现类、ImportBeanDefinitionRegistrar实现类
 * 3. 被导入的类不需要添加@Component等注解就能被Spring管理
 */
@Configuration
@Import({UserService.class, OrderService.class})
public class ImportConfig {
    
    public ImportConfig() {
        System.out.println("ImportConfig 构造函数被调用");
    }
} 