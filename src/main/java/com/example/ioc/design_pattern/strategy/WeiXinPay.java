package com.example.ioc.design_pattern.strategy;

import org.springframework.stereotype.Component;

@Component
public class WeiXinPay implements PayFacade {
    @Override
    public void pay() {
        System.out.println("执行微信支付");
    }

    @Override
    public Scene getSupportScene() {
        return Scene.TENCENT;
    }
}