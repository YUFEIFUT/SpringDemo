package com.example.ioc.strategy;

import org.springframework.beans.factory.InitializingBean;

public interface PayFacade extends InitializingBean {
    void pay();
    Scene getSupportScene();

    // spring 的生命周期中会自动执行这个的，所以就不用手动执行了
    @Override
    default void afterPropertiesSet() {
        PayFactory.register(getSupportScene(), this);
    }
}