package com.example.ioc.design_pattern.inwork;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// 通过IOC实现策略模式
public class TestMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.example.ioc.design_pattern.inwork");

        PayServiceFactory payServiceFactory = context.getBean(PayServiceFactory.class);

        payServiceFactory.getPayService("alipay").pay(new PayRequest("alipay"));
        payServiceFactory.getPayService("wechat").pay(new PayRequest("wechat"));

        context.close();
    }
}