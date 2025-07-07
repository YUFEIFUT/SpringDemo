package com.example.ioc.design_pattern.inwork;

import org.springframework.stereotype.Service;

@Service
public class WechatPayService extends AbstractPayService {
    @Override
    public void doPay(PayRequest payRequest) {
        //微信支付逻辑
        System.out.println("使用微信支付...........");
    }
}