package com.example.ioc.strategy;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// 通过IOC实现策略模式
public class TestMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.example.ioc.strategy");
        PayFacade tencentPay = PayFactory.get(Scene.TENCENT);
        tencentPay.pay();
        PayFacade alipay = PayFactory.get(Scene.ALIBABA);
        alipay.pay();
        context.close();
    }
}