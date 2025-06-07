package com.example.mixedconfigdemo;

import com.example.mixedconfigdemo.config.AppConfig;
import com.example.mixedconfigdemo.dao.UserDao;
import com.example.mixedconfigdemo.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        // 加载 Java 配置类（会自动导入 XML 配置）
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // 方式1：通过类型获取 UserService（默认使用 UserDaoImpl1，因为它被 @Repository 注解）
        UserService service1 = context.getBean(UserService.class);
        service1.registerUser(); // 输出：UserDaoImpl1: Saving user...

        // 方式2：通过名称获取 UserDaoImpl2（来自 XML 配置）
        UserDao userDao2 = context.getBean("userDao2", UserDao.class);
        System.out.println("Manually retrieved UserDao2: " + userDao2.getClass().getSimpleName());
    }
}