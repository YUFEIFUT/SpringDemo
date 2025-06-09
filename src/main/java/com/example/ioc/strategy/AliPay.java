package com.example.ioc.strategy;

import org.springframework.stereotype.Component;

@Component
public class AliPay implements PayFacade {
    @Override
    public void pay() {
        System.out.println("执行支付宝支付");
    }

    @Override
    public Scene getSupportScene() {
        return Scene.ALIBABA;
    }
}