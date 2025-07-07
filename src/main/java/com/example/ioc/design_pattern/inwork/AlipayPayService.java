package com.example.ioc.design_pattern.inwork;

import org.springframework.stereotype.Service;

@Service
public class AlipayPayService extends AbstractPayService {
    @Override
    public void doPay(PayRequest payRequest) {
        //支付宝支付逻辑
        System.out.println("使用支付宝支付...........");
    }
}