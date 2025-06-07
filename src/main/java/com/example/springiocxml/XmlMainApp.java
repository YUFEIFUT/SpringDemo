package com.example.springiocxml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlMainApp {
    public static void main(String[] args) {
        // 加载 XML 配置文件创建 Spring 容器
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 从容器中获取 MyXmlBean 实例
        MyXmlBean bean = context.getBean("myXmlBean", MyXmlBean.class);
        // 调用方法
        bean.use();
    }
}