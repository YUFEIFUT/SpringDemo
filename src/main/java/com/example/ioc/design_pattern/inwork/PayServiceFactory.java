package com.example.ioc.design_pattern.inwork;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PayServiceFactory implements InitializingBean {

    @Autowired
    public Map<String, PayService> payServiceMap = new ConcurrentHashMap<>();

    public PayService getPayService(String payChannel) {
        // alipay -> alipayPayService
        // wechat -> wechatPayService
        return payServiceMap.get(payChannel + "PayService");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println(payServiceMap);
    }
}